package com.example.testprojectwithdasha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectwithdasha.adapters.NewsGalleryAdapter;
import com.example.testprojectwithdasha.classes.OneImageFromGallery;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

public class NewsFragment extends Fragment {

    private String fullNewsText;
    private List<Bitmap> galleryList;
    public NewsFragment(String fullNewsText, List<Bitmap> galleryList) {
        this.fullNewsText = fullNewsText;
        this.galleryList = galleryList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        TextView textView = view.findViewById(R.id.tvNewsFullText);
        textView.setText(fullNewsText);
        RecyclerView recyclerView = view.findViewById(R.id.rvNewsGallery);
        if (galleryList.size() != 0) { // Если картинок в галерее нет, то зачем делать лишнюю работу
            NewsGalleryAdapter adapter = new NewsGalleryAdapter(galleryList);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
        }

        return view;
    }

    public  void backButtonWasPressed() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(NewsFragment.this).commit();
    }
}