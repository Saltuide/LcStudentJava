package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.annotation.SuppressLint;
import android.view.MotionEvent;

import com.example.testprojectwithdasha.adapters.MenuAdapter;
import com.google.android.material.tabs.TabItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button news_btn, back_btn, verification_btn;
    MenuAdapter adapter;
    private ListView buttons;
    private Button navigGoToRasp;
    private Button navigGoToNews;
    private Button navigGoToMenu;

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
                    }else if (position == 5) {
                        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    };
                };
            };
        });

        navigGoToRasp = (Button) findViewById(R.id.goto_rasp);
        navigGoToRasp.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    navigGoToRasp.setBackgroundResource(R.drawable.rasp_grey);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    navigGoToRasp.setBackgroundResource(R.drawable.rasp_white);
                    //open_RaspActivity();
                }

                return true;
            }
        });

        navigGoToNews = (Button) findViewById(R.id.goto_news);
        navigGoToNews.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    navigGoToNews.setBackgroundResource(R.drawable.news_grey);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    navigGoToNews.setBackgroundResource(R.drawable.news_white);
                    open_NewsActivity();
                }

                return true;
            }
        });

        navigGoToMenu = (Button) findViewById(R.id.goto_menu);
        navigGoToMenu.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    navigGoToMenu.setBackgroundResource(R.drawable.menu_darkgreen);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    navigGoToMenu.setBackgroundResource(R.drawable.menu_green);
                }

                return true;
            }
        });


    }

    /*public void open_RaspActivity() {
        Intent intent = new Intent(this, RaspActivity.class);
        startActivity(intent);
    }*/

    public void open_NewsActivity() {
        Intent intent = new Intent(this, NewsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);




    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

    }

}
