package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



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
        con.setDoOutput(true);
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
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
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
                try {
                    String s = postRequest(login.getText().toString(), password.getText().toString());
                    System.out.println(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        registration.setOnClickListener(oclBtn);

    }
}
