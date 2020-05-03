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
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class Log_inActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnLogin, btnRegistration, btnForgetPass;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        btnLogin = (Button) findViewById(R.id.log_in);
        btnLogin.setOnClickListener(this);
        btnRegistration = (Button) findViewById(R.id.registration_btn);
        btnRegistration.setOnClickListener(this);
        etEmail = (EditText) findViewById(R.id.e_mail_in);
        etPassword = (EditText) findViewById(R.id.password_in);
        btnForgetPass = (Button) findViewById(R.id.btnForgetPass);
        btnForgetPass.setOnClickListener(this);

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
            case R.id.btnForgetPass:
                Intent intent1 = new Intent(Log_inActivity.this, PasswordReset.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent1);
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
                etEmail.getText().toString(), etPassword.getText().toString());

        if(response.get("status").equals("true")){
            //Храним в настройках приложения новые данные
            SharedPreferences.Editor ed = MainActivity.sPref.edit();
            ed.putBoolean("State_of_the_input ", true);
            ed.putString("e_mail", etEmail.getText().toString());
            ed.commit();

            Intent intent = new Intent(Log_inActivity.this, MenuActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, response.get("comment"),Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
