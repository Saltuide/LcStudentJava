package com.example.testprojectwithdasha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        ListView groups_list = (ListView) findViewById(R.id.groups_list);
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < AboutAppActivity.sPref.getInt("group_count", 0); i++) {
            arrayList.add(AboutAppActivity.sPref.getString("group_name" + Integer.toString(i), ""));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayList);

        groups_list.setAdapter(adapter);

        groups_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(GroupsActivity.this);
                String message = AboutAppActivity.sPref.getString("faculty_name" + Integer.toString(position), "") +
                        "\n" + AboutAppActivity.sPref.getString("degree_program" + Integer.toString(position), "");
                builder1.setMessage(message);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Понятно",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            };
        });
    }
}
