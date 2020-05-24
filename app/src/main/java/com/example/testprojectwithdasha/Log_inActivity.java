package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class Log_inActivity extends AppCompatActivity implements View.OnClickListener {


    public static ArrayList<Object> my_arr;
    Button btnLogin, btnRegistration, btnForgetPass;
    EditText etEmail, etPassword;
    CheckBox checkBox;

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

        checkBox = (CheckBox) findViewById(R.id.checkpass);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    etPassword.setTransformationMethod(null);
                }
                etPassword.setSelection(etPassword.length());
            }
        });
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
            ed.putBoolean("status", true);
            ed.putString("e_mail", etEmail.getText().toString());
            ed.putBoolean("is_verificated", Boolean.parseBoolean(response.get("is_verificated")));
            System.out.println(response.get("last_name"));
            ed.putString("last_name", response.get("last_name"));
            ed.putString("first_name", response.get("first_name"));
            ed.putString("middle_name", response.get("middle_name") );

            ed.commit();

            RequestSender.getGroupsByUser(Log_inActivity.this, MainActivity.sPref.getString("e_mail", ""));

            Intent intent = new Intent(Log_inActivity.this, MenuActivity.class);
            startActivity(intent);
            Log_inActivity.this.finish();
        }else{
            Toast toast = Toast.makeText(this, response.get("comment"),Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
