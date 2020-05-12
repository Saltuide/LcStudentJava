package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Setting extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    private ListView setting_buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting_buttons = (ListView)findViewById(R.id.setting_buttons);
        adapter = ArrayAdapter.createFromResource(this, R.array.settings_menu,
                android.R.layout.simple_list_item_1);

        setting_buttons.setAdapter(adapter);

        setting_buttons.setAdapter(adapter);

        setting_buttons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences.Editor ed = MainActivity.sPref.edit();
                ed.putBoolean("status", false);
                ed.putString("e_mail", "");
                ed.putBoolean("is_verificated", false);
                ed.putString("last_name", "");
                ed.putString("first_name", "");
                ed.putString("middle_name", "");
                ed.commit();

                Intent intent = new Intent(Setting.this, Log_inActivity.class);
                startActivity(intent);
            }
        });
    }

}
