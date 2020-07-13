package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.testprojectwithdasha.adapters.SpinnerAdapter;
import com.example.testprojectwithdasha.classes.News;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SetData setData = new SetData();
        setData.execute();

    }

    @Override
    public void onClick(View v) {

    }


    class SetData extends AsyncTask<Void, Void, Void> {
        Bitmap mainImage;
        @Override
        protected Void doInBackground(Void... voids) {

            InputStream in = null;
            try {
                in = new java.net.URL("https://image-cdn.hypb.st/https%3A%2F%2Fhypebeast.com%2Fwp-content%2Fblogs.dir%2F6%2Ffiles%2F2019%2F06%2Fdick-worldwide-interview-instagram-penis-art-1.jpg?q=75&w=800&cbr=1&fit=max").openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainImage = BitmapFactory.decodeStream(in);
            return null;
        }
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            ImageView ivMainImage = findViewById(R.id.imageView);
            ivMainImage.setImageBitmap(mainImage);
            return;
        }
    }
}
