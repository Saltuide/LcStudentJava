package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.testprojectwithdasha.adapters.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    Button log_out;
    Spinner spinner;
    String[] cities = {"Москва", "Самара", "Вологда", "Волгоград", "Саратов", "Воронеж"};
    SpinnerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<String> arr = new ArrayList<>();


        arr.addAll(Arrays.asList(cities));

        spinner = (Spinner)findViewById(R.id.my_spinner);
        adapter = new SpinnerAdapter(this, arr);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
