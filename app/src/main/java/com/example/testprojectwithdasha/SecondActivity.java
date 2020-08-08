package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
//import android.widget.SpinnerAdapter;

import com.example.testprojectwithdasha.adapters.SpinnerSelectorAdapter;
import com.example.testprojectwithdasha.classes.SelectorsModel;
import com.example.testprojectwithdasha.adapters.SpinnerAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {


    private SpinnerSelectorAdapter selectorAdapter;
    List<SelectorsModel> mModelList;
    Spinner spinner, spinner1;
    private SpinnerAdapter spinnerAdapter;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        //mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_selector);
        //mRecyclerView.setVisibility(View.GONE);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner2);

        spinnerAdapter = new SpinnerAdapter(this, getList());
        spinner1.setAdapter(spinnerAdapter);

        selectorAdapter = new SpinnerSelectorAdapter(this, getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SecondActivity.this);
        //mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(manager);
        //mRecyclerView.setAdapter(selectorAdapter);
        spinner.setAdapter(selectorAdapter);

        //selectorAdapter.

        btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SelectorsModel> model = selectorAdapter.getModel();
                for (int i = 0; i < model.size(); i++) {
                    System.out.println(model.get(i).isSelected());
                }
            }
        });


    }

    private ArrayList<SelectorsModel> getListData() {
        mModelList = new ArrayList<>();
        ArrayList<String> att = new ArrayList<>();
        att.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
        for (int i = 0; i <= 12; i++) {
            mModelList.add(new SelectorsModel(att.get(i)));
        }
        return (ArrayList<SelectorsModel>) mModelList;
    }

    private ArrayList<String> getList() {
            ArrayList<String> att = new ArrayList<>();
            att.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
            return att;
        }

    @Override
    public void onClick(View v) {
        FragmentManager f = getSupportFragmentManager();
//        BlankFragment b = BlankFragment.newInstance();
//        b.show(f, "kappa");

    }
}
