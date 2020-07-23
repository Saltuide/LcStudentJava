package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

//Пусть эта хрень будет для экспериментов
public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    PhotoView attacher;
    PhotoViewAttacher a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Drawable drawable = getResources().getDrawable(R.drawable.error_pic);
        img = findViewById(R.id.imageView2);
        img.setImageDrawable(drawable);
        //img.setImageDrawable(drawable);
        a = new PhotoViewAttacher(img);

//        attacher = findViewById(R.id.photo_view);
//        attacher.setImageDrawable(drawable);



    }

    @Override
    public void onClick(View v) {
        FragmentManager f = getSupportFragmentManager();
//        BlankFragment b = BlankFragment.newInstance();
//        b.show(f, "kappa");

    }
}
