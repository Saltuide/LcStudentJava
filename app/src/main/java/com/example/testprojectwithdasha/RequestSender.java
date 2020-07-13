package com.example.testprojectwithdasha;

import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

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
import java.nio.charset.Charset;
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

    public static Map<String, String> requestLogin(Activity context, String login,
                                                   String password) throws Exception{
        JSONObject body = new JSONObject();

        String newString = new String(login.getBytes("UTF-8"), "ISO-8859-1");

        body.put("email", newString);
        body.put("password", password);

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.login_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        System.out.println(stringBody);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(loginLink, stringBody, postRequest);
        //преобработка ответа (убираем крайние кавычки)
        response = response.replace("\"", "");

        Properties props = new Properties();
        try {
            props.load(new StringReader(response.substring(1, response.length() - 1).replace(
                    ", ", "\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> parsedJSON = new HashMap<String, String>();
        //собираем JSON
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            parsedJSON.put((String)e.getKey(), (String)e.getValue());
        }

        return parsedJSON;
    }

    public static Map<String, String> requestRegistration(Activity context, String login,
                                                          String password) throws Exception {
        JSONObject body = new JSONObject();
        body.put("email", login);
        body.put("password", password);

        String stringBody = body.toString();
        String regLink =  context.getResources().getString(R.string.reg_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(regLink, stringBody, postRequest);
        //преобработка ответа (убираем крайние кавычки)
        response = response.replace("\"", "");

        Properties props = new Properties();
        try {
            props.load(new StringReader(response.substring(1, response.length() - 1).replace(
                    ", ", "\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> parsedJSON = new HashMap<String, String>();
        //собираем JSON
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            parsedJSON.put((String)e.getKey(), (String)e.getValue());
        }

        return parsedJSON;
    }

    public static Map<String, String> sRequestResetPassword(Activity context, String email)
            throws Exception{
        JSONObject body = new JSONObject();
        body.put("email", email);

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.reset_password_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(loginLink, stringBody, postRequest);
        //преобработка ответа (убираем крайние кавычки)
        response = response.replace("\"", "");

        Properties props = new Properties();
        try {
            props.load(new StringReader(response.substring(1, response.length() - 1).replace(
                    ", ", "\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> parsedJSON = new HashMap<String, String>();
        //собираем JSON
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            parsedJSON.put((String)e.getKey(), (String)e.getValue());
        }
        return parsedJSON;
    }
    
    public static void getGroupsByUser (Activity context, String email) throws Exception{
        //email ="admin";
        SharedPreferences.Editor ed = MainActivity.sPref.edit();
        JSONObject body = new JSONObject();
        body.put("email", email);

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.getGroupsByUser_link);
        String postRequest = context.getResources().getString(R.string.post_request);
        //посылаем запрос на сервер и получаем ответ в response
        String response = requestSender(loginLink, stringBody, postRequest);

        JSONObject objJson = new JSONObject(response);

        //if (objJson.getBoolean("status")) {
        JSONArray featuresArr = objJson.getJSONArray("answer");
        ed.putInt("group_count", featuresArr.length());

        for (int i = 0; i < featuresArr.length(); i++) {
            ed.putString("group_name" + Integer.toString(i), featuresArr.getJSONObject(i).getString("group_name"));
            ed.putString("degree_program"+ Integer.toString(i), featuresArr.getJSONObject(i).getString("degree_program"));
            ed.putString("faculty_name"+ Integer.toString(i), featuresArr.getJSONObject(i).getString("faculty_name"));
            ed.commit();
        }
    }

    public static String getNews(Activity context, String type, String year, String month,
                                 String tag, int page){
        JSONObject jsonObject = new JSONObject();
        try{
            if(type == "default") jsonObject.put("type", type);
            else{
                jsonObject.put("type", type);
                jsonObject.put("year", year);
                jsonObject.put("month", month);
                jsonObject.put("tag", tag);
                jsonObject.put("current_page", page);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
