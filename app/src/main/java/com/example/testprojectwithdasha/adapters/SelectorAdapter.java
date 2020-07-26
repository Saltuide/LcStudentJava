package com.example.testprojectwithdasha.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectwithdasha.R;
import com.example.testprojectwithdasha.classes.SelectorsModel;

import java.util.List;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.MyViewHolder> {

    private List<SelectorsModel> mModelList;

    public SelectorAdapter(List<SelectorsModel> modelList) {
        mModelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selector_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SelectorsModel model = mModelList.get(position);
        holder.textView.setText(model.getText());
        holder.view.setBackgroundColor(model.isSelected() ? Color.rgb(53, 24, 82) : Color.rgb(201, 153, 188));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.rgb(53, 24, 82) : Color.rgb(201, 153, 188));
            }
        });
    }

    public void soutModel() {
        for (int i = 0; i < mModelList.size(); i++) {
            System.out.println(mModelList.get(i).isSelected());
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public List<SelectorsModel> getmModelList() {
        return mModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = (TextView) itemView.findViewById(R.id.selector_txt);
        }
    }
}