package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class Log_inActivity extends AppCompatActivity implements View.OnClickListener {


    Button log_in, registartion;
    EditText e_mail, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        log_in = (Button) findViewById(R.id.log_in);
        log_in.setOnClickListener(this);
        registartion = (Button) findViewById(R.id.registration_btn);
        registartion.setOnClickListener(this);
        e_mail = (EditText) findViewById(R.id.e_mail_in);
        password = (EditText) findViewById(R.id.password_in);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_in:
                try {
                    Log_in();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.registration_btn:
                System.out.println("scjdsd");
                Intent intent = new Intent(Log_inActivity.this, RegistrationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Log_in() throws Exception {
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Map<String, String> response = RequestSender.requestLogin(Log_inActivity.this,
                e_mail.getText().toString(), password.getText().toString());

        if(response.get("status").equals("true")){

            //Храним в настройках приложения новые данные
            SharedPreferences.Editor ed = MainActivity.sPref.edit();
            ed.putBoolean("State_of_the_input ", true);
            ed.putString("e_mail", e_mail.getText().toString());
            ed.commit();

            Intent intent = new Intent(Log_inActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            System.out.println("YOU ARE NOT ALLOWED TO CUM");
        }

    }
}
