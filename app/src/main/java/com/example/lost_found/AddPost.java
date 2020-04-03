package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPost extends AppCompatActivity {
    Intent getMtoA;
    EditText etitle,eitem,etime,elocation,ediscribe;
    RadioButton lost,found;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getMtoA=getIntent();
        etitle=(EditText)findViewById(R.id.ap_title);
        eitem=(EditText)findViewById(R.id.ap_item);
        etime=(EditText)findViewById(R.id.ap_time);
        elocation=(EditText)findViewById(R.id.ap_location);
        ediscribe=(EditText)findViewById(R.id.ap_describe);
        lost=(RadioButton)findViewById(R.id.rb_lost);
        found=(RadioButton)findViewById(R.id.rb_found);
        userid=getMtoA.getStringExtra("userid");
    }
    public void PUBLISH(View v) throws Exception {
        String title,item,time,location,discribe;
        title=etitle.getText().toString();
        item=eitem.getText().toString();
        time=etime.getText().toString();
        location=elocation.getText().toString();
        discribe=ediscribe.getText().toString();
        String url="http://192.168.0.103:8080/ADDPost.php";
        String result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("userid",userid);
        jsonobj.put("title",title);
        jsonobj.put("item",item);
        jsonobj.put("time",time);
        jsonobj.put("location",location);
        jsonobj.put("discribe",discribe);
        if(lost.isChecked()){
        jsonobj.put("type","lost");
        }else{
            jsonobj.put("type","found");
        }
        result=MyThread.Show(url,jsonobj,this,result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        etitle.setEnabled(false);
        eitem.setEnabled(false);
        etime.setEnabled(false);
        elocation.setEnabled(false);
        ediscribe.setEnabled(false);
    }
}
