package com.example.testprojectwithdasha.classes;

import android.graphics.Bitmap;

public class OneImageFromGallery {
    private Bitmap image;

    public OneImageFromGallery(Bitmap image){
        this.image = image;
    }

    public Bitmap getImage() {
        return this.image;
    }
}
