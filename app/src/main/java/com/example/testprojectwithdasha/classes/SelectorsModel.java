package com.example.testprojectwithdasha.classes;

public class SelectorsModel {

    private String text;
    private boolean isSelected = false;

    public SelectorsModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}