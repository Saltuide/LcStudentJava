package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectwithdasha.NewsActivity;
import com.example.testprojectwithdasha.R;

import java.util.List;

public class NewsGalleryAdapter extends RecyclerView.Adapter<NewsGalleryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Bitmap> images;

    public NewsGalleryAdapter(List<Bitmap> images){
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
        Bitmap currentImage = images.get(position);
        holder.imageButton.setImageBitmap(currentImage);
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private NewsGalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NewsGalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
