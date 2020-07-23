package com.example.testprojectwithdasha.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testprojectwithdasha.R;
import com.github.chrisbanes.photoview.PhotoView;


import java.util.List;


public class NewsGalleryFragment extends DialogFragment {

    private MyViewPagerAdapter myViewPagerAdapter;
    private ViewPager viewPager;
    private int selectedPosition = 0;
    private List<Bitmap> allImagesForGallery;
    private TextView counter;


    public NewsGalleryFragment() {
        // Required empty public constructor
    }

    public static NewsGalleryFragment newInstance(int position, List<Bitmap> allImagesForGallery) {
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
            //Возвращаем картинкам исходный размер, на случай если они были зазумлены
            /*тут порой происходит ошибка в строке
            PhotoView currentPhoto = view.findViewById(R.id.image_preview)
            Attempt to invoke virtual method 'android.view.View android.view.View.findViewById(int)'
            on a null object reference
            причем так было, когда я обращался просто по position, без цикла. Если кто поймет, как
            это починить нормальным образом - your welcome.
            P.S. фор нужен, чтобы точно все зарезумить, а то бывали косяки.
            */
            try {
                for (int i = 0; i < position + 1; i++) {
                    View view = viewPager.getChildAt(i);
                    PhotoView currentPhoto = view.findViewById(R.id.image_preview);
                    currentPhoto.setScale(currentPhoto.getMinimumScale());
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
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

            layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container,
                    false);

            PhotoView photoView = view.findViewById(R.id.image_preview);
            Bitmap bitmap = allImagesForGallery.get(position);
            photoView.setImageBitmap(bitmap);

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