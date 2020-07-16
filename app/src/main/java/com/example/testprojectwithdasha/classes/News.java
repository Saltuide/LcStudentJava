package com.example.testprojectwithdasha.classes;

import android.graphics.Bitmap;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class News {
    private String title;
    private String tag;
    private String description;
    private String pubDate;
    private Bitmap mainImage;
    private String fullText;
    private List<OneImageFromGallery> galleryImages;

public News(String title, String tag, String pubDate, String description, Bitmap mainImage,
            String fullText, List<OneImageFromGallery> galleryImages){
        this.title = title;
        this.tag = tag;
        this.pubDate = pubDate;
        this.description = description;
        this.mainImage = mainImage;
        this.fullText = fullText;
        this.galleryImages = galleryImages;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public Bitmap getMainImage(){
        return this.mainImage;
    }

    public String getFullText(){
        return this.fullText;
    }

    public List<OneImageFromGallery> getGalleryImages() {
        return this.galleryImages;
    }
}
