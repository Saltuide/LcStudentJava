package com.example.testprojectwithdasha.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.testprojectwithdasha.NewsActivity;
import com.example.testprojectwithdasha.R;
import com.example.testprojectwithdasha.adapters.NewsGalleryAdapter;

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


        recyclerView.addOnItemTouchListener(new NewsGalleryAdapter.RecyclerTouchListener(
                getContext(), recyclerView, new NewsGalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putLis("images", galleryList);
//                bundle.putInt("position", position);

                FragmentManager ft = getFragmentManager();

                BlankFragment newFragment = BlankFragment.newInstance(galleryList, position);
                newFragment.show(ft, "kappa");



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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