package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class NewsActivity extends AppCompatActivity implements View.OnClickListener{

    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener((View.OnClickListener) this);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                Intent intent = new Intent(NewsActivity.this, MenuActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
