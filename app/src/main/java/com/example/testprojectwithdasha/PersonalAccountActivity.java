package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.testprojectwithdasha.adapters.GroupsAdapter;
import com.example.testprojectwithdasha.adapters.PersonalAccountAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalAccountActivity<CustomerDataSource> extends AppCompatActivity {

    private CustomerDataSource datasource;
    private long mStartTime = 0L;
    private int count = 0;
    ListView listView;
    public ListView listViewGroups;
    String[] cities = {"Москва", "Самара", "Вологда", "Волгоград", "Саратов", "Воронеж"};


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);

        listView = (ListView) findViewById(R.id.listView);
        listViewGroups = (ListView) findViewById(R.id.listViewGroups);
        ArrayList<ArrayList<HashMap<String, String>>> groupArrayList = new ArrayList<>();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        arrayList = Functions.getUserSettings();
        groupArrayList = Functions.getUserGroups();

        PersonalAccountAdapter adapter = new PersonalAccountAdapter(this, arrayList);
        listView.setAdapter(adapter);

        if(groupArrayList.size() == 1) {

            PersonalAccountAdapter adapter_group = new PersonalAccountAdapter(this, groupArrayList.get(0));

            listViewGroups.setAdapter(adapter_group);
        } else if (MainActivity.sPref.getInt("group_count",0) > 1) {
            GroupsAdapter adapter_group = new GroupsAdapter(this, groupArrayList, 0);
            listViewGroups.setAdapter(adapter_group);

            //mHandler.postDelayed(mUpdateUITimerTask, 100 * 100);
        }


    }

/*    private final Runnable mUpdateUITimerTask = new Runnable() {
        public void run() {
            count += 1;
            int group_num = count % MainActivity.sPref.getInt("group_count", 0);

            ListView listViewGroups = (ListView) findViewById(R.id.listViewGroups);
            ArrayList<ArrayList<HashMap<String, String>>> groupArrayList = new ArrayList<>();
            groupArrayList = Functions.getUserGroups();
            HashMap<String, String> map;

            PersonalAccountAdapter adapter_group = new PersonalAccountAdapter(PersonalAccountActivity.this, groupArrayList.get(group_num));
            listViewGroups.setAdapter(adapter_group);

            if (count <= 50) {
                mHandler.postDelayed(mUpdateUITimerTask, 100 * 100);
            }
        }
    };*/
    private final Handler mHandler = new Handler();
}
