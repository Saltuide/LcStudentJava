package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private newsItemFragment test;
    private NewsFragment scrollTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SetData setData = new SetData();
        setData.execute();

        ImageButton imgBtn = findViewById(R.id.imageButton);
        imgBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        System.out.println("gggGGGGggg");
        FragmentManager fm = getSupportFragmentManager();

        test = new newsItemFragment();
        scrollTest = new NewsFragment("");

//        fm.beginTransaction().replace(R.id.sexAct, test).commit();
        fm.beginTransaction().replace(R.id.sexAct, scrollTest).commit();
    }

    @Override
    public void onBackPressed() {
//        if(test.isVisible()){
//            test.backButtonWasPressed();
//        }else {
//            super.onBackPressed();
//        }
        if(scrollTest.isVisible()){
            scrollTest.backButtonWasPressed();
        }else {
            super.onBackPressed();
        }
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
            ImageButton ivMainImage = findViewById(R.id.imageButton);
            ivMainImage.setImageBitmap(mainImage);
            return;
        }
    }
}
