package com.example.lost_found;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

class MyThread extends Thread
{

    private static String serverUrl;
    private static JSONObject jsonobj;
    public static Context context;
    public static String result;
    public void run()
    {
        try{
            //将参数连接到stringBuilder中
            StringBuilder stringBuilder=new StringBuilder();
            Iterator it=jsonobj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jsonobj.getString(key);
                stringBuilder.append(key).append("=").append(value);
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);//删除最后一个&
            String data=stringBuilder.toString();
            /*data="{\"test\":\"test data\"}";*/

            System.out.println("上传的数据是："+data);

            //网络连接
            URL url=new URL(serverUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            OutputStream out=new DataOutputStream(conn.getOutputStream()) ;
            out.write(data.getBytes());
            out.close();
            if(conn.getResponseCode()==200){

                // 获取响应的输入流对象
                System.out.println(conn.getContent());
                InputStream in=conn.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
//                while ((line=reader.readLine())!=null){
//                    Log.d("success",line);
//                }
                if((line=reader.readLine())!=null){
                    Looper.prepare();
                  /*  Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,"success to upload",Toast.LENGTH_SHORT).show();*/
                    result=line;
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(context,"failed!",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception e){
            e.printStackTrace();
            Looper.prepare();//子线程不能直接toast
            Toast.makeText(context,"fail to upload,please ensure your computer and phone on the same network",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static String Show(final String url, final JSONObject jsonOBJ, final Context cont,String Fresult) throws InterruptedException {
        serverUrl=url;
        jsonobj=jsonOBJ;
        context=cont;
        result=Fresult;
        System.out.println("开始");
        MyThread t1 = new MyThread();
        t1.start();
        while(result == null)
        {
            sleep(100);
        }
        System.out.println("成功");
        System.out.println(result);
        return result;
    }


}