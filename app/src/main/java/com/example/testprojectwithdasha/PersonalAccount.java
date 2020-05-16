package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PersonalAccount<CustomerDataSource> extends AppCompatActivity {

    private CustomerDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);

        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;


        map = new HashMap<>();
        map.put("Name", "ФИО");
        map.put("Value", MainActivity.sPref.getString("last_name", "") + " " + MainActivity.sPref.getString("first_name", "") + " " + MainActivity.sPref.getString("middle_name", ""));
        arrayList.add(map);

        if (!MainActivity.sPref.getString("group_name", "").equals("")) {

            map = new HashMap<>();
            map.put("Name", "Факультет");
            map.put("Value", MainActivity.sPref.getString("faculty_name", ""));
            arrayList.add(map);

            map = new HashMap<>();
            map.put("Name", "Направление");
            map.put("Value", MainActivity.sPref.getString("degree_program", ""));
            arrayList.add(map);

            map = new HashMap<>();
            map.put("Name", "Группа");
            map.put("Value", MainActivity.sPref.getString("group_name", ""));
            arrayList.add(map);

        }


        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Value"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }
}
