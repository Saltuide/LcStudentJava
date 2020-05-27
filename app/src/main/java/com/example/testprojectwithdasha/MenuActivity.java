package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.testprojectwithdasha.adapters.MenuAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button news_btn, back_btn, verification_btn;
    MenuAdapter adapter;
    private ListView buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttons = (ListView)findViewById(R.id.buttons);
        final Button btn = (Button)findViewById(R.id.btn);

        if (MainActivity.sPref.getBoolean("is_verificated", false)) {
            ArrayList<String> att = new ArrayList<>();
            att.addAll(Arrays.asList(getResources().getStringArray(R.array.menu_button_verificated)));
            adapter = new MenuAdapter(this, att);
        } else {
            ArrayList<String> att = new ArrayList<>();
            att.addAll(Arrays.asList(getResources().getStringArray(R.array.menu_button)));
            adapter = new MenuAdapter(this, att);
        }

        buttons.setAdapter(adapter);

        buttons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MainActivity.sPref.getBoolean("is_verificated", false)) {
                    if (position == 0) {
                        Intent intent = new Intent(MenuActivity.this, PersonalAccountActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else if (position == 2){
                        Intent intent = new Intent(MenuActivity.this, NewsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else if (position == 7) {
                        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    };
                };
            };
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

    }

}
