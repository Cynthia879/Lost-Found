package com.example.lost_found;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    Intent MtoL;
    CheckBox accept;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accept=(CheckBox)findViewById(R.id.check_accept);
        enter=(Button)findViewById(R.id.btn_enter);
        MtoL=new Intent(this,LoginRegister.class);
        accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enter.setVisibility(View.VISIBLE);
                }else{
                    enter.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void EnterAPP(View view){
        startActivity(MtoL);
    }

}
