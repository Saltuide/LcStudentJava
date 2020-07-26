package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testprojectwithdasha.adapters.SelectorAdapter;
import com.example.testprojectwithdasha.classes.SelectorsModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SelectorAdapter selectorAdapter;
    List<SelectorsModel> mModelList;
    Button btn;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_selector);
        mRecyclerView.setVisibility(View.GONE);
        selectorAdapter = new SelectorAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(SecondActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(selectorAdapter);

        //selectorAdapter.

        btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count ++;
                if (count % 2 == 1) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });


    }

    private List<SelectorsModel> getListData() {
        mModelList = new ArrayList<>();
        ArrayList<String> att = new ArrayList<>();
        att.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
        for (int i = 0; i <= 12; i++) {
            mModelList.add(new SelectorsModel(att.get(i)));
        }
        return mModelList;
    }

    @Override
    public void onClick(View v) {
        FragmentManager f = getSupportFragmentManager();
//        BlankFragment b = BlankFragment.newInstance();
//        b.show(f, "kappa");

    }
}
