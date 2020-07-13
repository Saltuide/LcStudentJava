package com.example.testprojectwithdasha;

import java.util.ArrayList;
import java.util.HashMap;

public class Functions {
    public static ArrayList<HashMap<String, String>> getUserSettings() {
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        map = new HashMap<>();
        map.put("Name", "Фамилия");
        map.put("Value", MainActivity.sPref.getString("last_name", ""));
        arrayList.add(map);

        map = new HashMap<>();
        map.put("Name", "Имя");
        map.put("Value",MainActivity.sPref.getString("first_name", ""));
        arrayList.add(map);

        if (!MainActivity.sPref.getString("middle_name", "").equals("")) {
            map = new HashMap<>();
            map.put("Name", "Отчество");
            map.put("Value", MainActivity.sPref.getString("middle_name", ""));
            arrayList.add(map);
        }

        return arrayList;

    }


    public static ArrayList<ArrayList<HashMap<String, String>>> getUserGroups() {
        HashMap<String, String> map;
        ArrayList<ArrayList<HashMap<String, String>>> group_ArrayList = new ArrayList<>();


        for (int i = 0; i < MainActivity.sPref.getInt("group_count", 0); i++) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

            map = new HashMap<>();
            map.put("Name", "Группа");
            map.put("Value", MainActivity.sPref.getString("group_name" + Integer.toString(i), ""));
            arrayList.add(map);

            map = new HashMap<>();
            map.put("Name", "Направление");
            map.put("Value",MainActivity.sPref.getString("degree_program" + Integer.toString(i), ""));
            arrayList.add(map);

            map = new HashMap<>();
            map.put("Name", "Факультет");
            map.put("Value",MainActivity.sPref.getString("faculty_name" + Integer.toString(i), ""));
            arrayList.add(map);

            group_ArrayList.add(arrayList);
        }


        return group_ArrayList;

    }
}
