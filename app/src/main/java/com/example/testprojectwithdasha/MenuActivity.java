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
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button news_btn, back_btn, verification_btn;
    ArrayAdapter<CharSequence> adapter;
    private ListView buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttons = (ListView)findViewById(R.id.buttons);
        final Button btn = (Button)findViewById(R.id.btn);

        if (MainActivity.sPref.getBoolean("is_verificated", false)) {
            adapter = ArrayAdapter.createFromResource(this, R.array.menu_button_verificated,
                    android.R.layout.simple_list_item_1);
        } else {

            adapter = ArrayAdapter.createFromResource(this, R.array.menu_button,
                    android.R.layout.simple_list_item_1);
        }

        buttons.setAdapter(adapter);

        buttons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MainActivity.sPref.getBoolean("is_verificated", false)) {
                    if (position == 0) {
                        Intent intent = new Intent(MenuActivity.this, PersonalAccount.class);
                        startActivity(intent);
                    } else if (position == 7) {
                        Intent intent = new Intent(MenuActivity.this, Setting.class);
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
