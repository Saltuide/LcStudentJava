package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener {
    Button then;
    public static SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        then = (Button)findViewById(R.id.thenbtn);
        then.setOnClickListener((View.OnClickListener) this);

        //Получаем настройки приложения
        sPref = getPreferences(MODE_PRIVATE);
        Boolean State_of_the_input  = sPref.getBoolean("status", false);

        Bundle arguments = getIntent().getExtras();

        //пока что параметры могут прийти только из активити "о приложении"
        if (State_of_the_input && arguments == null) {
            Intent intent = new Intent(AboutAppActivity.this, MenuActivity.class);
            startActivity(intent);
            AboutAppActivity.this.finish();
        }

    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thenbtn) {
            //Получаем настройки приложения
            sPref = getPreferences(MODE_PRIVATE);
            Boolean StateOfTheInput  = sPref.getBoolean("status", false);

            if (StateOfTheInput) {
                Intent intent = new Intent(AboutAppActivity.this, MenuActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(AboutAppActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            AboutAppActivity.this.finish();
        }
    }

}
