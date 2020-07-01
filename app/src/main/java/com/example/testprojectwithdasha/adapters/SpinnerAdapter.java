package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testprojectwithdasha.R;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    ArrayList<String> my_array;
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(Context context, ArrayList<String> my_array) {
        this.my_array = my_array;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return my_array.size();
    }

    @Override
    public Object getItem(int position) {
        return my_array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView text = (TextView)view.findViewById(R.id.spinner_txt);


        text.setText(getString(position));
        return view;

    }

    private String getString(int position) {
        return (String) getItem(position);
    }
}
