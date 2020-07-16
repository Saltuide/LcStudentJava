package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectwithdasha.NewsFragment;
import com.example.testprojectwithdasha.R;
import com.example.testprojectwithdasha.classes.OneImageFromGallery;

import java.util.List;

public class NewsGalleryAdapter extends RecyclerView.Adapter<NewsGalleryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<OneImageFromGallery> images;

    public NewsGalleryAdapter(List<OneImageFromGallery> images){
        //this.inflater = LayoutInflater.from(context);
        this.images = images;
}

    public NewsGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_for_news_gallery,
                null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void onBindViewHolder(NewsGalleryAdapter.ViewHolder holder, int position){
        OneImageFromGallery currentImage = images.get(position);
        holder.imageButton.setImageBitmap(currentImage.getImage());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ImageButton imageButton;
        ViewHolder(View view){
            super(view);
            imageButton = view.findViewById(R.id.imgFromNewsGallery);
        }
    }

    public int getItemCount(){
        if (images == null)
            return 0;

        return images.size();
    }
}
