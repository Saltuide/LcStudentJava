package com.example.testprojectwithdasha;

import android.app.Activity;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RequestSender {

    private static final int CONNECTION_TIMEOUT = 5000;

    public static String requestSender(String link, String body, String requestType){
        URL url;
        try {
             url = new URL(link);
        } catch (MalformedURLException e) {
            return "Проблема в ссылке";
        }

        HttpURLConnection con;
        try{
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            return "Проблема в установлении соединения";
        }

        try {
            con.setRequestMethod(requestType);
        } catch (ProtocolException e) {
            return "Проблема в задании типа соединения";
        }

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);

        //Cериализация
        try {
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
        } catch (Exception e) {
            return "Проблема в сериализации";
        }

        try{
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            //Считываем ответ и записываем в строку response
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        }catch (IOException e) {
            return "Проблема в считывании ответа";
        }

    }

    public static String requestLogin(Activity context, String login,
                                                   String password) {
        JSONObject body = new JSONObject();
        String validLogin;
        try{
             validLogin = new String(login.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return "Логин содержит некорректные символы";
        }

        try {
            body.put("email", validLogin);
            body.put("password", password);
        } catch (JSONException e) {
            return "that's not suppose to ever happen";
        }

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.login_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(loginLink, stringBody, postRequest);


        return response;
    }

    public static String requestRegistration(Activity context, String login,
                                                          String password){
        JSONObject body = new JSONObject();
        try {
            body.put("email", login);
            body.put("password", password);
        } catch (JSONException e) {
            return "that's not suppose to ever happen";
        }

        String stringBody = body.toString();
        String regLink =  context.getResources().getString(R.string.reg_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(regLink, stringBody, postRequest);

        return response;
    }

    public static String sRequestResetPassword(Activity context, String email) {
        JSONObject body = new JSONObject();

        try {
            body.put("email", email);
        } catch (JSONException e) {
            return "that's not suppose to ever happen";
        }

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.reset_password_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(loginLink, stringBody, postRequest);

        return response;
    }
    
    public static String  getGroupsByUser (Activity context, String email){
        //email ="admin";
        SharedPreferences.Editor ed = AboutAppActivity.sPref.edit();
        JSONObject body = new JSONObject();
        try{
            body.put("email", email);
        } catch (JSONException e) {
            return "Переменная не положилась в json _-_";

        }

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.getGroupsByUser_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(loginLink, stringBody, postRequest);

        JSONObject objJson;
        try {
            objJson = new JSONObject(response);
        } catch (JSONException e) {
            return "Не удалось преобразовать json";
        }

        JSONArray featuresArr;
        try {
            featuresArr = objJson.getJSONArray("answer");
        } catch (JSONException e) {
            return "Не удалось получить ответ";
        }
        ed.putInt("group_count", featuresArr.length());

        Boolean check = true;
        for (int i = 0; i < featuresArr.length(); i++) {
            try {
                ed.putString("group_name" + i, featuresArr.getJSONObject(i).getString("group_name"));
                ed.putString("degree_program" + i, featuresArr.getJSONObject(i).getString("degree_program"));
                ed.putString("faculty_name" + i, featuresArr.getJSONObject(i).getString("faculty_name"));
                ed.commit();
            } catch (JSONException e) {
                check = false;
            }
        }
        if(check)
            return "Все ок";
        return "Инфа о какой-то группе не добавилась";
    }

    public static String getNews(Activity context, String type, ArrayList<String> year, ArrayList<String> month,
                                 ArrayList<String> tag, int page) throws JSONException {
        String yearArrayToStr = "";
        String monthArrayToStr = "";
        String tagArrayToStr = "";
        JSONObject jsonObject = new JSONObject();
//        ArrayList<String> year = new ArrayList<String>();
//        year.add("2019");
//        year.add("2020");
        try{// TODO: 13.08.2020 fix costyl
            if(type == "default"){
                System.out.println("here");
                jsonObject.put("type", type);
            }else{
                type = "custom";
                if(year.get(0) != "all"){
                    for(int i = 0; i < year.size(); i++){
                        yearArrayToStr += year.get(i) + ",";
                    }
                } else{
                    yearArrayToStr = "all";
                }

                if(month.get(0) != "all"){
                    for(int i = 0; i < year.size(); i++){
                        monthArrayToStr += month.get(i) + ",";
                    }
                } else{
                    monthArrayToStr = "all";
                }

                if(tag.get(0) != "all"){
                    for(int i = 0; i < tag.size(); i++){
                        tagArrayToStr += month.get(i) + ",";
                    }
                } else{
                    tagArrayToStr = "all";
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject.put("type", type);
        jsonObject.put("year", yearArrayToStr);
        jsonObject.put("month", monthArrayToStr);
        jsonObject.put("tag", tagArrayToStr);
        jsonObject.put("current_page", page);
        
        System.out.println("список годов выглядит так: " + jsonObject.get("year"));
        String stringBody = jsonObject.toString();
        String newsLink =  context.getResources().getString(R.string.get_news);
        String postRequest = context.getResources().getString(R.string.post_request);

        String ans = requestSender(newsLink, stringBody, postRequest);
        return ans;
    }

    public static String verification (Activity context, String email, String name, String surname,
                                        String middleName, String birthday, String passport){


        JSONObject jsonObject = new JSONObject();

        try {
                jsonObject.put("email", email);
                jsonObject.put("surname", surname);
                jsonObject.put("name", name);
                jsonObject.put("middle_name", middleName);
                jsonObject.put("date_of_birth", birthday);
                jsonObject.put("last_numbers_passport", passport);
            } catch (JSONException e) {
                System.out.println("ALO NAHUI");
        }

        String stringBody = jsonObject.toString();

        System.out.println(stringBody);

        String verificationLink = context.getResources().getString(R.string.verification_link);
        String postRequest = context.getResources().getString(R.string.post_request);

        String answer = requestSender(verificationLink, stringBody, postRequest);
        return answer;

    }
}
