package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.testprojectwithdasha.adapters.MenuAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    MenuAdapter adapter;
    private ListView setting_buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ArrayList<String> att = new ArrayList<>();
        att.addAll(Arrays.asList(getResources().getStringArray(R.array.settings_menu)));

        setting_buttons = (ListView)findViewById(R.id.setting_buttons);
        adapter = new MenuAdapter(this, att);

        setting_buttons.setAdapter(adapter);

        setting_buttons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Кнопка выхода
                if (position == 1) {
                    SharedPreferences.Editor ed = MainActivity.sPref.edit();
                    ed.putBoolean("status", false);
                    ed.putString("e_mail", "");
                    ed.putBoolean("is_verificated", false);
                    ed.putString("last_name", "");
                    ed.putString("first_name", "");
                    ed.putString("middle_name", "");
                    ed.putString("faculty_name", "");
                    ed.putString("degree_program", "");
                    ed.putString("group_name", "");
                    ed.commit();

                    LoginActivity.my_arr = new ArrayList<>();

                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    // Чистим вилкой кэш активити
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                };
            };
        });
    }

}
