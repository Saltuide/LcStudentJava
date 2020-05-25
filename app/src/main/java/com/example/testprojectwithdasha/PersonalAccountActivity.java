package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalAccountActivity<CustomerDataSource> extends AppCompatActivity {

    private CustomerDataSource datasource;
    private long mStartTime = 0L;
    private int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);

        ListView listView = (ListView) findViewById(R.id.listView);
        ListView listViewGroups = (ListView) findViewById(R.id.listViewGroups);
        ArrayList<HashMap<String, String>> groupArrayList = new ArrayList<>();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;


        map = new HashMap<>();
        map.put("Name", "Фамилия");
        map.put("Value", MainActivity.sPref.getString("last_name", ""));
        arrayList.add(map);

        map = new HashMap<>();
        map.put("Name", "Имя");
        map.put("Value",MainActivity.sPref.getString("first_name", ""));
        arrayList.add(map);

        if (!MainActivity.sPref.getString("middle_name", "").equals("")) {
            map = new HashMap<>();
            map.put("Name", "Отчество");
            map.put("Value", MainActivity.sPref.getString("middle_name", ""));
            arrayList.add(map);
        }


        if(MainActivity.sPref.getInt("group_count",0) == 1) {
            map = new HashMap<>();
            map.put("Name", "Группа");
            map.put("Value", MainActivity.sPref.getString("group_name0", ""));
            groupArrayList.add(map);

            SimpleAdapter adapter_group = new SimpleAdapter(this, groupArrayList, android.R.layout.simple_list_item_2,
                    new String[]{"Name", "Value"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listViewGroups.setAdapter(adapter_group);
        } else if (MainActivity.sPref.getInt("group_count",0) > 1) {
            map = new HashMap<>();
            map.put("Name", "Группа");
            map.put("Value", MainActivity.sPref.getString("group_name" + "0", ""));
            groupArrayList.add(map);

            SimpleAdapter adapter_group = new SimpleAdapter(PersonalAccountActivity.this, groupArrayList, android.R.layout.simple_list_item_2,
                    new String[]{"Name", "Value"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listViewGroups.setAdapter(adapter_group);

            mHandler.postDelayed(mUpdateUITimerTask, 10 * 100);
        }



        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Value"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (MainActivity.sPref.getInt("group_count",0) == 1) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PersonalAccountActivity.this);
                    String message = MainActivity.sPref.getString("faculty_name" + Integer.toString(position), "") +
                            "\n" + MainActivity.sPref.getString("degree_program" + Integer.toString(position), "");
                    builder1.setMessage(message);
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Понятно",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    Intent intent = new Intent(PersonalAccountActivity.this, GroupsActivity.class);
                    startActivity(intent);
                };
            };
        });
    }

    private final Runnable mUpdateUITimerTask = new Runnable() {
        public void run() {
            count += 1;
            int group_num = count % MainActivity.sPref.getInt("group_count", 0);

            ListView listViewGroups = (ListView) findViewById(R.id.listViewGroups);
            ArrayList<HashMap<String, String>> groupArrayList = new ArrayList<>();
            HashMap<String, String> map;

            map = new HashMap<>();
            map.put("Name", "Группа");
            map.put("Value", MainActivity.sPref.getString("group_name" + Integer.toString(group_num), ""));
            groupArrayList.add(map);

            SimpleAdapter adapter_group = new SimpleAdapter(PersonalAccountActivity.this, groupArrayList, android.R.layout.simple_list_item_2,
                    new String[]{"Name", "Value"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            listViewGroups.setAdapter(adapter_group);

            if (count <= 50) {
                mHandler.postDelayed(mUpdateUITimerTask, 10 * 100);
            }
        }
    };
    private final Handler mHandler = new Handler();
}
