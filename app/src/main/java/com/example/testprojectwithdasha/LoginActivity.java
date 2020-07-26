package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
                Intent intent = new Intent(LoginActivity.this,
                        RegistrationActivity.class);
                startActivity(intent);
                break;
            case R.id.btnForgetPass:
                Intent intent1 = new Intent(LoginActivity.this,
                        PasswordResetActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent1);
            default:
                break;
        }
    }

    
    private void Log_in(){
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().
                    permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String response = RequestSender.requestLogin(LoginActivity.this,
                etEmail.getText().toString(), etPassword.getText().toString());

        JSONObject answer;
        try{
             answer = new JSONObject(response);
        } catch (JSONException e) {
            Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            return;
        }

        try{
            if(answer.getString("status").equals("true")){
                //Храним в настройках приложения новые данные
                SharedPreferences.Editor ed = AboutAppActivity.sPref.edit();
                ed.putBoolean("status", true);
                ed.putString("e_mail", etEmail.getText().toString());
                ed.putBoolean("is_verificated", Boolean.parseBoolean(answer.getString("is_verificated")));
                ed.putString("last_name", answer.getString("last_name"));
                ed.putString("first_name", answer.getString("first_name"));
                ed.putString("middle_name", answer.getString("middle_name") );
                ed.commit();

                String getGroupAnswer = RequestSender.getGroupsByUser(LoginActivity.this,
                        AboutAppActivity.sPref.getString("e_mail", ""));

                if (!getGroupAnswer.equals("Все ок")){
                    Toast.makeText(this, "Не удалось получить информацию о группах",
                            Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }else{
                Toast toast = Toast.makeText(this, answer.getString("comment"),Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Неизвестная ошибка при считывании ответа",
                    Toast.LENGTH_LONG).show();
        }

    }
}
