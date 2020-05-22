package com.example.testprojectwithdasha;

import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RequestSender {

    private static final int CONNECTION_TIMEOUT = 5000;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Map<String, String> requestLogin(Activity context, String login, String password) throws Exception{
        JSONObject body = new JSONObject();
        body.put("email", login);
        body.put("password", password);

        String stringBody = body.toString();
        String loginLink =  context.getResources().getString(R.string.login_link);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Map<String, String> requestRegistration(Activity context, String login, String password) throws Exception {
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Map<String, String> sRequestResetPassword(Activity context, String email) throws Exception{
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //
    public static String requestSender(String link, String body, String requestType) throws Exception{
        final URL url = new URL(link);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestType);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);
        //Cериализация
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(body);
            wr.flush();
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();
            //Считываем ответ и записываем в строку response
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
}
