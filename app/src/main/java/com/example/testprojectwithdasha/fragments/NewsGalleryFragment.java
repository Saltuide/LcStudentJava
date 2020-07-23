package com.example.testprojectwithdasha.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.testprojectwithdasha.R;

import java.util.List;


public class NewsGalleryFragment extends DialogFragment {

    private MyViewPagerAdapter myViewPagerAdapter;
    private ViewPager viewPager;
    private int selectedPosition = 0;
    private List<String> allImagesForGallery;
    private TextView counter;

    public NewsGalleryFragment() {
        // Required empty public constructor
    }

    public static NewsGalleryFragment newInstance(int position, List<String> allImagesForGallery) {
        NewsGalleryFragment fragment = new NewsGalleryFragment();
        fragment.selectedPosition = position;
        fragment.allImagesForGallery = allImagesForGallery;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news_gallery, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        counter = v.findViewById(R.id.tvPicNumNewsGal);
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);

        return v;
    }


    private void setCurrentItem(int position){
        viewPager.setCurrentItem(position, false);
        setCounterInfo(position);
    }

    private void setCounterInfo(int position){
        counter.setText((position + 1) + " из " + (allImagesForGallery.size()));
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setCounterInfo(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = view.findViewById(R.id.image_preview);

            String image = allImagesForGallery.get(position);

            Glide.with(getActivity()).load(image)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return allImagesForGallery.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == (obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}