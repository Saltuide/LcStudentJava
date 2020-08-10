package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testprojectwithdasha.classes.MyOnFocusChangeListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText e_mail, password, repeat_password;
    private Button log_in, registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        e_mail = findViewById(R.id.e_mail_reg);
        password = findViewById(R.id.password_reg);
        repeat_password = findViewById(R.id.repeat_password_reg);

        registration = findViewById(R.id.registration);
        registration.setOnClickListener(this);
        log_in = findViewById(R.id.log_in_btn);
        log_in.setOnClickListener(this);

        e_mail.setOnFocusChangeListener(new MyOnFocusChangeListener(e_mail));
        password.setOnFocusChangeListener(new MyOnFocusChangeListener(password));
        repeat_password.setOnFocusChangeListener(new MyOnFocusChangeListener(repeat_password));
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_in_btn:
                Intent intent = new Intent(RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.registration:
                if (password.getText().toString().compareTo(repeat_password.getText().
                        toString()) == 0) {
                    try {
                        registration();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast toast = Toast.makeText(this, "Пароли не совпадают",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            default:
                break;
        }
    }

    
    private void registration() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
                permitAll().build();
        StrictMode.setThreadPolicy(policy);


        String response = RequestSender.requestRegistration(RegistrationActivity.this,
                e_mail.getText().toString(), password.getText().toString());

        JSONObject answer;
        try{
             answer = new JSONObject(response);
        } catch (JSONException e) {
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (answer.getString("status").equals("true")) {
                //Храним в настройках приложения новые данные
                SharedPreferences.Editor ed = AboutAppActivity.sPref.edit();
                ed.putBoolean("State_of_the_input ", true);
                ed.putString("e_mail", e_mail.getText().toString());
                ed.commit();

                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, answer.getString("comment"),
                        Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Неизвестная ошибка при считывании ответа",
                    Toast.LENGTH_LONG).show();
        }
    }
}
