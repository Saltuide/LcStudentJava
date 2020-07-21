package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btn = findViewById(R.id.button123);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager f = getSupportFragmentManager();
//        BlankFragment b = BlankFragment.newInstance();
//        b.show(f, "kappa");

    }
}
