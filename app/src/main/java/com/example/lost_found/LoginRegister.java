package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class LoginRegister extends AppCompatActivity {
    EditText eID,ePW;
    Intent LtoS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        eID=(EditText)findViewById(R.id.edit_id);
        ePW=(EditText)findViewById(R.id.edit_password);
        LtoS=new Intent(this,ShowContent.class);
    }

    public void Login(View v) throws Exception {
        String id=eID.getText().toString();
        String password=ePW.getText().toString();
        String url="http://192.168.0.103:8080/Login.php";
        String result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("userid",id);
        jsonobj.put("password",password);
        result=MyThread.Show(url,jsonobj,this,result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        System.out.println(result);
        if(result.equals("Login success")){
            LtoS.putExtra("userid",id);
            startActivity(LtoS);
        }
    }
    public void Register(View v) throws Exception {
        String id=eID.getText().toString();
        String password=ePW.getText().toString();
        String url="http://192.168.0.103:8080/Register.php";
        String result=null;
        JSONObject jsonobj=new JSONObject();
        jsonobj.put("userid",id);
        jsonobj.put("password",password);
        jsonobj.put("name","null");
        jsonobj.put("college","null");
        jsonobj.put("major","null");
        jsonobj.put("action","register");
        result=MyThread.Show(url,jsonobj,this,result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        System.out.println(result);
    }
}
