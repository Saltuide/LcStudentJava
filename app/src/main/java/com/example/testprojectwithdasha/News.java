package com.example.testprojectwithdasha;

import java.util.ArrayList;

public class News {
    private String title;
    private String tag;
    //private String mainImage;
    //private ArrayList<String> otherImages;
    private String description;
    //private String mainText;
    //private String link;
    private String pubDate;

//    News(String title, String tag, String mainImage, ArrayList<String> otherImages,
//         String description, String mainText, String link, String pubDate){
//        this.title = title;
//        this.tag = tag;
//        this.mainImage = mainImage;
//        this.otherImages = otherImages;
//        this.description = description;
//        this.mainText = mainText;
//        this.link = link;
//        this.pubDate = pubDate;
//    }
    News(String title, String tag, String pubDate, String description){
        this.title = title;
        this.tag = tag;
        this.pubDate = pubDate;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
