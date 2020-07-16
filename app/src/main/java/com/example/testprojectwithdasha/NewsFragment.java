package com.example.testprojectwithdasha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

public class NewsFragment extends Fragment {

    private String fullNewsText;
    public NewsFragment(String fullNewsText) {
        this.fullNewsText = fullNewsText;
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        TextView textView = view.findViewById(R.id.tvNewsFullText);
        textView.setText(fullNewsText);
        return view;
    }



    public  void backButtonWasPressed() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(NewsFragment.this).commit();
    }
}