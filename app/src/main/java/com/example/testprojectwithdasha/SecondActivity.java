package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    Button log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        log_out = (Button)findViewById(R.id.logout);
        log_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout) {
            SharedPreferences.Editor ed = MainActivity.sPref.edit();
            ed.putBoolean("State_of_the_input ", false);
            ed.putString("e_mail", "");
            ed.commit();

            Intent intent = new Intent(SecondActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
