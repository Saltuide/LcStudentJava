package com.example.testprojectwithdasha;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testprojectwithdasha.adapters.GroupsAdapter;
import com.example.testprojectwithdasha.adapters.PersonalAccountAdapter;
import com.example.testprojectwithdasha.adapters.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalAccountActivity<CustomerDataSource> extends AppCompatActivity {

    public static ArrayList<ArrayList<HashMap<String, String>>> groupArrayList = new ArrayList<>();
    public static ListView listViewGroups;
    private int count = 0;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);

        ListView listView = (ListView) findViewById(R.id.listView);
        listViewGroups = (ListView) findViewById(R.id.listViewGroups);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        final TextView name_text = (TextView)findViewById(R.id.name_txt);
        Spinner value_spinner = (Spinner)findViewById(R.id.value_spinner);
        View view = (View)findViewById(R.id.view);
        view.setVisibility(View.VISIBLE);

        arrayList = Functions.getUserSettings();
        groupArrayList = Functions.getUserGroups();

        PersonalAccountAdapter adapter = new PersonalAccountAdapter(this, arrayList);
        listView.setAdapter(adapter);

        if (groupArrayList.size() > 1){
            GroupsAdapter adapter_group = new GroupsAdapter(PersonalAccountActivity.this, groupArrayList.get(0));
            listViewGroups.setAdapter(adapter_group);

            name_text.setText("Группа");

            ArrayList<String> arr = new ArrayList<>();

            for (int i = 0; i < AboutAppActivity.sPref.getInt("group_count", 0); i++) {
                arr.add(AboutAppActivity.sPref.getString("group_name" + Integer.toString(i), ""));
            }

            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, arr);
            value_spinner.setAdapter(spinnerAdapter);


            value_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    GroupsAdapter adapter_group = new GroupsAdapter(PersonalAccountActivity.this, groupArrayList.get(position));
                    listViewGroups.setAdapter(adapter_group);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } else if (groupArrayList.size() == 1) {
            value_spinner.setVisibility(View.GONE);
            name_text.setVisibility(View.GONE);
            PersonalAccountAdapter adapter_group = new PersonalAccountAdapter(this, groupArrayList.get(0));

            arrayList.addAll(groupArrayList.get(0));
            adapter = new PersonalAccountAdapter(this, arrayList);
            listView.setAdapter(adapter);
            //listViewGroups.setAdapter(adapter_group);
            listViewGroups.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }


    }

    //
    /*private final Runnable mUpdateUITimerTask = new Runnable() {
        public void run() {
            count += 1;
            int group_num = count % AboutAppActivity.sPref.getInt("group_count", 0);

            ListView listViewGroups = (ListView) findViewById(R.id.listViewGroups);
            ArrayList<HashMap<String, String>> groupArrayList = new ArrayList<>();
            HashMap<String, String> map;

            map = new HashMap<>();
            map.put("Name", "Группа");
            map.put("Value", AboutAppActivity.sPref.getString("group_name" + Integer.toString(group_num), ""));
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
    private final Handler mHandler = new Handler();*/

    private void setAdapter(ArrayList<HashMap<String, String>> settingList,ArrayList<ArrayList<HashMap<String, String>>> GroupList, int my_position){

    }
}
