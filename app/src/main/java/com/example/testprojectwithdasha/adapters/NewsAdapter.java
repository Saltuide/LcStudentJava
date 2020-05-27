package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectwithdasha.R;
import com.example.testprojectwithdasha.classes.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<News> news;

    public NewsAdapter(Context context, List<News> news){
        this.news = news;
        this.inflater = LayoutInflater.from(context);
    }

    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position){
        News oneNews = news.get(position);
        holder.titleView.setText(oneNews.getTitle());
        holder.tagView.setText(oneNews.getTag());
        holder.descriptionView.setText(oneNews.getDescription());
        holder.pubDateView.setText(oneNews.getPubDate());
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        final TextView titleView, tagView, pubDateView, descriptionView;
        ViewHolder(View view){
            super(view);
            titleView = (TextView) view.findViewById(R.id.tvTitle);
            tagView = (TextView) view.findViewById(R.id.tvTag);
            pubDateView = (TextView) view.findViewById(R.id.tvPubDate);
            descriptionView = (TextView) view.findViewById(R.id.tvDescription);
        }
    }

    public int getItemCount(){
        return news.size();
    }

}
