package com.example.testprojectwithdasha.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testprojectwithdasha.R;
import com.example.testprojectwithdasha.classes.SelectorsModel;

import java.util.ArrayList;
import java.util.List;

public class SpinnerSelectorAdapter extends BaseAdapter {

    ArrayList<SelectorsModel> mModelList;
    private LayoutInflater layoutInflater;

    public SpinnerSelectorAdapter(Context context, ArrayList<SelectorsModel> my_array) {
        this.mModelList = my_array;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.selector_item, parent, false);
        }

        //тут цвета подумать вечером с Сёмой
        final SelectorsModel model = (SelectorsModel) getItem(position);
        TextView text = view.findViewById(R.id.selector_txt);
        text.setText(getString(model));
        view.setBackgroundResource(model.isSelected() ? R.drawable.spinner_item_selected : R.drawable.spinner_item);

        if (position > 0) {
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        model.setSelected(!model.isSelected());
                    view.setBackgroundResource(model.isSelected() ? R.drawable.spinner_item_selected : R.drawable.spinner_item);
                }
            });
        }

        return view;

    }

    private String getString(SelectorsModel model) {
        return model.getText();
    }

    public ArrayList<SelectorsModel> getModel () {
        return mModelList;
    }
}
