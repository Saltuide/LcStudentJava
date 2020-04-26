package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

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


public class MainActivity extends AppCompatActivity {

    private static final int CONNECTION_TIMEOUT = 5000;
    Button registration;
    TextInputEditText password, login;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String postRequest(String login, String password) throws Exception{
        final URL url = new URL("http://10.0.2.2:8000/sign_in/");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);

        JSONObject body = new JSONObject();
        body.put("email", login);
        body.put("password", password);

        String urlParameters = body.toString();

        // Send post request
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        registration = (Button) findViewById(R.id.register);
        password  = (TextInputEditText) findViewById(R.id.password);
        login = (TextInputEditText) findViewById(R.id.login);

        View.OnClickListener oclBtn = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String answer = new String();
                try {
                    answer = postRequest(login.getText().toString(), password.getText().toString());
                    answer = answer.replace("\"", "");
                    System.out.println(answer);
                    //{"status": true, "is_authorized": false, "id": "dasha@ya.ru"}
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Properties props = new Properties();
                try {
                    props.load(new StringReader(answer.substring(1, answer.length() - 1).replace(", ", "\n")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map<String, String> parsedJSON = new HashMap<String, String>();
                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    parsedJSON.put((String)e.getKey(), (String)e.getValue());
                }
                if(parsedJSON.get("status").equals("true")){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }else{
                    System.out.println("YOU ARE NOT ALLOWED TO CUM");
                }

            }
        };
        registration.setOnClickListener(oclBtn);

    }
}
