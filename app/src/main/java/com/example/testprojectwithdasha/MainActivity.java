package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


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
            Boolean State_of_the_input  = sPref.getBoolean("State_of_the_input ", false);

            if (State_of_the_input) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, Log_inActivity.class);
                startActivity(intent);
            }
        }
    }

}
