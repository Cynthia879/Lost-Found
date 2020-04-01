package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginRegister extends AppCompatActivity {
    URL url;
    EditText eID,ePW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        eID=(EditText)findViewById(R.id.edit_id);
        ePW=(EditText)findViewById(R.id.edit_password);

    }

    public void Login(View v){
        String id=eID.getText().toString();
        String password=ePW.getText().toString();
        try {
            url = new URL("http://192.168.0.103:8080/test.php");
            URLConnection rulConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.connect();
            OutputStream outStrm = httpUrlConnection.getOutputStream();
            ObjectOutputStream objOutputStrm = new ObjectOutputStream(outStrm);
            objOutputStrm.writeObject(new String("我是测试数据"));
            objOutputStrm.flush();
            objOutputStrm.close();
            InputStream inStrm = httpUrlConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Register(View v){}
}
