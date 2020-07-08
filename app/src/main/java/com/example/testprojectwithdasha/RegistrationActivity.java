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

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CONNECTION_TIMEOUT = 5000;
    private EditText e_mail, password, repeat_password;
    private Button log_in, registration;

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

    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_in_btn:
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.registration:
                if (password.getText().toString().compareTo(repeat_password.getText().toString()) == 0) {
                    try {
                        registration();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast toast = Toast.makeText(this, "Пароли не совпадают",Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            default:
                break;
        }
    }

    
    private void registration() throws Exception {
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Map<String, String> response = RequestSender.requestRegistration(RegistrationActivity.this,
                e_mail.getText().toString(), password.getText().toString());

        if(response.get("status").equals("true")){

            //Храним в настройках приложения новые данные
            SharedPreferences.Editor ed = MainActivity.sPref.edit();
            ed.putBoolean("State_of_the_input ", true);
            ed.putString("e_mail", e_mail.getText().toString());
            ed.commit();

            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, response.get("comment"),Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
