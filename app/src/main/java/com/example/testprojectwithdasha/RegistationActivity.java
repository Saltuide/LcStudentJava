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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RegistationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CONNECTION_TIMEOUT = 5000;
    EditText e_mail, password, repeat_password;
    Button log_in, registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        e_mail = (EditText)findViewById(R.id.e_mail_reg);
        password = (EditText)findViewById(R.id.password_reg);
        repeat_password = (EditText)findViewById(R.id.repeat_password_reg);

        registration = (Button)findViewById(R.id.registration);
        registration.setOnClickListener(this);
        log_in = (Button)findViewById(R.id.log_in_btn);
        log_in.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_in_btn:
                Intent intent = new Intent(RegistationActivity.this, Log_inActivity.class);
                startActivity(intent);
                break;
            case R.id.registration:
                if (password.getText().toString().compareTo(repeat_password.getText().toString()) == 0) {
                    registration();
                } else {
                    System.out.println("passwords don't match");
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void registration() {
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String answer = new String();
        try {
            answer = MainActivity.postRequest(e_mail.getText().toString(), password.getText().toString());
            answer = answer.replace("\"", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties props = new Properties();

        try {
            props.load(new StringReader(answer.substring(1, answer.length() - 1).replace(", ", "\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> parsedJSON = new HashMap<String, String>();

        for (Map.Entry<Object, Object> e : props.entrySet()) {
            parsedJSON.put((String)e.getKey(), (String)e.getValue());
        }

        if(parsedJSON.get("status").equals("true")){

            //Храним в настройках приложения новые данные
            SharedPreferences.Editor ed = MainActivity.sPref.edit();
            ed.putBoolean("State_of_the_input ", true);
            ed.putString("e_mail", e_mail.getText().toString());
            ed.commit();

            Intent intent = new Intent(RegistationActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
            System.out.println("registration failed");
        }
    }
}
