package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testprojectwithdasha.PersonalAccountActivity;
import com.example.testprojectwithdasha.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupsAdapter extends BaseAdapter {
    ArrayList<ArrayList<HashMap<String, String>>> my_array;
    private LayoutInflater layoutInflater;
    int my_position;

    public GroupsAdapter(Context context, ArrayList<ArrayList<HashMap<String, String>>> my_array, int myPosition) {
        this.my_array = my_array;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        my_position = myPosition;
    }


    @Override
    public int getCount() {
        return my_array.get(0).size();
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

        TextView text1 = (TextView)view.findViewById(R.id.text1_name);
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        TextView text2 = (TextView)view.findViewById(R.id.text1_value);

        if (position == 0) {
            ArrayList<String> arr = new ArrayList<>();

            for (int i = 0; i < my_array.size(); i++) {
                arr.add(my_array.get(i).get(0).get("Value"));
            }


            text1.setText("Группа");
            spinner.setVisibility(View.VISIBLE);
            SpinnerAdapter adapter = new SpinnerAdapter(view.getContext(), arr);
            spinner.setAdapter(adapter);

            text2.setVisibility(View.INVISIBLE);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            HashMap<String, String> map;
            map = getStringString(position, my_position);

            //System.out.println(map.get(0));
            text1.setText(map.get("Name"));
            text2.setText(map.get("Value"));
            spinner.setVisibility(View.INVISIBLE);
        }



        return view;

    }

    private ArrayList<HashMap<String, String>> getStringArray(int position) {
        return (ArrayList<HashMap<String, String>>) getItem(position);
    }

    private HashMap<String, String> getStringString(int position, int my_position) {
        return (HashMap<String, String>) getStringArray(my_position).get(position);
    }

    public void setMy_position(int i) {
        my_position = i;
    }

}
