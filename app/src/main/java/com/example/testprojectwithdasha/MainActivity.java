package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testprojectwithdasha.adapters.MenuAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CONNECTION_TIMEOUT = 5000;
    Button then;
    public static SharedPreferences sPref;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        then = (Button)findViewById(R.id.thenbtn);
        then.setOnClickListener((View.OnClickListener) this);

    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thenbtn) {

            //Получаем настройки приложения
            sPref = getPreferences(MODE_PRIVATE);
            Boolean StateOfTheInput  = sPref.getBoolean("status", false);

            if (StateOfTheInput) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            MainActivity.this.finish();
        }
    }

}
