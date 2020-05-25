package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvInfoText, tvResetEmail;
    Button btnResetPass, btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        tvInfoText = (TextView) findViewById(R.id.tvInfoText);
        tvResetEmail = (TextView) findViewById(R.id.tvResetEmail);
        btnResetPass = (Button) findViewById(R.id.btnResetPass);
        btnBackToLogin = (Button) findViewById(R.id.btnBackToLogin);
        btnResetPass.setOnClickListener(this);
        btnBackToLogin.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void resetPassword(){
        String email = tvResetEmail.getText().toString();
        System.out.println(email);
        try {
            Map<String, String> response = RequestSender.sRequestResetPassword(PasswordResetActivity.this, email);
            System.out.println(response.get("status"));
            if(response.get("status").equals("true")){
                tvInfoText.setText("На вашу почту отправлено письмо с ссылкой для восстановления" +
                        " пароля. Перейдите по ней для смены пароля в течение одного дня.");
            }else{
                Toast toast = Toast.makeText(this, response.get("comment"),Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnResetPass:
                resetPassword();
                break;
            case R.id.btnBackToLogin:
                Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
                startActivity(intent);
                PasswordResetActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
