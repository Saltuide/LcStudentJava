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

public class GroupsAdapter extends BaseAdapter {
    ArrayList<ArrayList<HashMap<String, String>>> my_array;
    private LayoutInflater layoutInflater;

    public GroupsAdapter(Context context, ArrayList<ArrayList<HashMap<String, String>>> my_array) {
        this.my_array = my_array;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 1;
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
            view = layoutInflater.inflate(R.layout.string_spinner_item, parent, false);
        }


        TextView text1 = (TextView)view.findViewById(R.id.name_text);
        Spinner spinner = (Spinner)view.findViewById(R.id.value_text);

        text1.setText("Группа");

        ArrayList<String> arr = new ArrayList<>();

        for (int i = 0; i < my_array.size(); i++) {
            arr.add(my_array.get(i).get(0).get("Value"));
        }

        SpinnerAdapter adapter = new SpinnerAdapter(view.getContext(), arr);
        spinner.setAdapter(adapter);

        return view;
        
    }

}
