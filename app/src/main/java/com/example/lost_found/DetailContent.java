package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailContent extends AppCompatActivity {
    Intent getStoD;
    String title,item,type,time,location,describe,userid;
    String result;
    String []data;
    int dataLength;
    TextView t_title,t_item,t_type,t_time,t_location,t_describe;
    JSONArray jsonArray;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_post);
        getStoD=getIntent();
        userid=getStoD.getStringExtra("userid");
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        title=getStoD.getStringExtra("title");
        item=getStoD.getStringExtra("item");
        type=getStoD.getStringExtra("type");
        time=getStoD.getStringExtra("time");
        location=getStoD.getStringExtra("location");
        describe=getStoD.getStringExtra("describe");

        t_title=(TextView) findViewById(R.id.dp_title);
        t_item=(TextView) findViewById(R.id.dp_item);
        t_type=(TextView) findViewById(R.id.dp_type);
        t_time=(TextView) findViewById(R.id.dp_time);
        t_location=(TextView) findViewById(R.id.dp_location);
        t_describe=(TextView) findViewById(R.id.dp_describe);

        t_title.setText(title);
        t_item.setText(item);
        t_type.setText(type);
        t_time.setText(time);
        t_location.setText(location);
        t_describe.setText(describe);

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
                dataLength=data.length;
            }
            ListView listView = (ListView) findViewById(R.id.dp_reply);//在视图中找到ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
            listView.setAdapter(adapter);
        }
    }

    public void reply(View v) throws Exception {
        EditText e_reply=(EditText)findViewById(R.id.edit_reply);
        String reply=e_reply.getText().toString();
        if(reply.length()!=0){
            String url="http://192.168.0.103:8080/ADDReply.php";
            JSONObject jsonobj=new JSONObject();
            jsonobj.put("title",title);
            jsonobj.put("userid",userid);
            jsonobj.put("replycontent",reply);
            MyThread.Show(url,jsonobj,this,"");
            dataLength++;
            String []temp=new String[dataLength];
            for(int i=0;i<dataLength-1;i++){
                temp[i]=data[i];
            }
            temp[dataLength-1]="replyID: "+userid+"\nreply cotent: "+reply;
            data=new String[dataLength];
            for(int i=0;i<dataLength;i++){
                data[i]=temp[i];
            }
            ListView listView = (ListView) findViewById(R.id.dp_reply);//在视图中找到ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
            listView.setAdapter(adapter);
            e_reply.setText("");
            Toast.makeText(this, "Comment published", Toast.LENGTH_SHORT).show();
        }
    }

}
