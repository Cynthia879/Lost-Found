package com.example.lost_found;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManagePost extends AppCompatActivity {
    Intent getManager;
    String title,item,type,time,location,describe;
    EditText e_title,e_item,e_type,e_time,e_location,e_describe;
    String result;
    JSONArray jsonArray;
    String []data;
    boolean flag_modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_mypost);
        getManager=getIntent();
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        flag_modify=false;
    }

    public void init() throws Exception {
        e_title=(EditText)findViewById(R.id.mp_title);
        e_item=(EditText)findViewById(R.id.mp_item);
        e_type=(EditText)findViewById(R.id.mp_type);
        e_time=(EditText)findViewById(R.id.mp_time);
        e_location=(EditText)findViewById(R.id.mp_location);
        e_describe=(EditText)findViewById(R.id.mp_describe);

        title=getManager.getStringExtra("title");
        item=getManager.getStringExtra("item");
        type=getManager.getStringExtra("type");
        time=getManager.getStringExtra("time");
        location=getManager.getStringExtra("location");
        describe=getManager.getStringExtra("describe");

        e_title.setText(title);
        e_item.setText(item);
        e_type.setText(type);
        e_time.setText(time);
        e_location.setText(location);
        e_describe.setText(describe);

        e_title.setEnabled(false);
        e_item.setEnabled(false);
        e_type.setEnabled(false);
        e_time.setEnabled(false);
        e_location.setEnabled(false);
        e_describe.setEnabled(false);

        getReply();
    }

    public void getReply() throws Exception {
        String url="http://192.168.0.103:8080/searchReply.php";
        result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("title",title);
        result=MyThread.Show(url,jsonobj,this,result);
        if(!result.equals("no data")){
            result=result.substring(0,result.length()-1);
            result+="]";
        }else{
            result=null;
        }
        getJsonArr();
    }

    public void getJsonArr() throws Exception {
        if(result==null)
            return;
        else{
            jsonArray=new JSONArray(result);
            int l=jsonArray.length();
            data=new String[l];
            for(int i=0;i<l;i++){
                JSONObject jobj=jsonArray.getJSONObject(i);
                String s_reply="replyID: "+jobj.getString("replyid")+"\nreply cotent: "+jobj.getString("replycontent");
                data[i]=s_reply;
            }
            ListView listView = (ListView) findViewById(R.id.mp_reply);//在视图中找到ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
            listView.setAdapter(adapter);
        }
    }

    public void MODIFY(View v){
        if(!flag_modify){
            flag_modify=true;
        Button modify=(Button)findViewById(R.id.btn_modify);
        modify.setText("upload");
        e_item.setEnabled(true);
        e_type.setEnabled(true);
        e_time.setEnabled(true);
        e_location.setEnabled(true);
        e_describe.setEnabled(true);
        }else if(flag_modify){

            AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to upload your post")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            item=e_item.getText().toString();
                            type=e_type.getText().toString();
                            time=e_time.getText().toString();
                            location=e_location.getText().toString();
                            describe=e_describe.getText().toString();
                            if(type.equals("lost")||type.equals("found")){
                                try {
                                    uploadModify();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(ManagePost.this, "Please fill in type with lost or found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })

                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            e_item.setText(item);
                            e_type.setText(type);
                            e_time.setText(time);
                            e_location.setText(location);
                            e_describe.setText(describe);
                        }
                    })
                    .create();
            alertDialog2.show();

            Button modify=(Button)findViewById(R.id.btn_modify);
            modify.setText("MODIFY");
            e_item.setEnabled(false);
            e_type.setEnabled(false);
            e_time.setEnabled(false);
            e_location.setEnabled(false);
            e_describe.setEnabled(false);
            flag_modify=false;
        }
    }

    public void deletePost() throws Exception {
        String url="http://192.168.0.103:8080/DeletePost.php";
        result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("title",title);
        result=MyThread.Show(url,jsonobj,this,result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
    public void uploadModify() throws Exception {
        String url="http://192.168.0.103:8080/UpdatePost.php";
        result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("title",title);
        jsonobj.put("item",item);
        jsonobj.put("type",type);
        jsonobj.put("time",time);
        jsonobj.put("location",location);
        jsonobj.put("describe",describe);
        result=MyThread.Show(url,jsonobj,this,result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        System.out.println(result);
    }
    public void DELETE(View v){
        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete your post")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            deletePost();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ManagePost.this, "CANCELLED", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        alertDialog2.show();
    }
}
