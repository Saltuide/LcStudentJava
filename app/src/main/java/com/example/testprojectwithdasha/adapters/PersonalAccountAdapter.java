package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testprojectwithdasha.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PersonalAccountAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> my_array;
    private LayoutInflater layoutInflater;

    public PersonalAccountAdapter(Context context, ArrayList<HashMap<String, String>> my_array) {
        this.my_array = my_array;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return  my_array.size();
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
            view = layoutInflater.inflate(R.layout.personal_account_item, parent, false);
        }


        TextView text_name = (TextView)view.findViewById(R.id.name_txt);
        TextView text_value = (TextView)view.findViewById(R.id.value_txt);

        HashMap<String, String> map;
        map = getStringString(position);

        //System.out.println(map.get(0));
        text_name.setText(map.get("Name"));
        text_value.setText(map.get("Value"));

        return view;

    }

    private HashMap<String, String> getStringString(int position) {
        return (HashMap<String, String>) getItem(position);
    }
}
