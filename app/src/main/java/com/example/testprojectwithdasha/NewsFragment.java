package com.example.testprojectwithdasha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

public class NewsFragment extends Fragment {

    public NewsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    public  void backButtonWasPressed() {
        NewsFragment f = new NewsFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.remove(NewsFragment.this).commit();
    }
}