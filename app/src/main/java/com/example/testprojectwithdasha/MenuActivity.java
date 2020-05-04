package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button news_btn, back_btn, verification_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        news_btn = (Button) findViewById(R.id.news_btn);
        news_btn.setOnClickListener((View.OnClickListener) this);
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener((View.OnClickListener) this);
        verification_btn = (Button) findViewById(R.id.verification_btn);
        verification_btn.setOnClickListener((View.OnClickListener) this);

        String id_1c  = MainActivity.sPref.getString("1c_id", "");

        if (id_1c != "") {
            verification_btn.setVisibility(View.INVISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_btn:
                try {
                    Intent intent = new Intent(MenuActivity.this, NewsActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.back_btn:
                Intent intent = new Intent(MenuActivity.this, Log_inActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
