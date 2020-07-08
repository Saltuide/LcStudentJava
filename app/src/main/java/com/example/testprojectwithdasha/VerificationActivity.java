package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button verification;
    private EditText email, surname, name, batyaName, day, month, year, passport;
    private  ConstraintLayout mainLayout;

    private Pattern pattern;
    private Matcher matcher;
    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    protected String email_, passport_, finalName, finalSurname, finalBatyaName, birthday_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificataion);

        mainLayout = findViewById(R.id.clVerifMain);

        verification = findViewById(R.id.btnVerif);
        verification.setOnClickListener(this);

        email = findViewById(R.id.etEmailVerif);
        surname = findViewById(R.id.etSurnameVerif);
        name = findViewById(R.id.etNameVerif);
        batyaName = findViewById(R.id.etBatyaNameVerif);
        day = findViewById(R.id.etDayVerif);
        month = findViewById(R.id.etMonthVerif);
        year = findViewById(R.id.etYearVerif);
        passport = findViewById(R.id.etPassportVerif);
    }

    private boolean isDateValid(String day, String month, String year){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = day + "." + month + "." + year;
        myFormat.setLenient(false);
        try {
            myFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isEmailValid(String email){
        pattern = Pattern.compile(EMAIL_PATTERN); //копмилируем перед использованием (так быстрее)
        matcher = pattern.matcher(email); //задание строки для проверки с регуляркой
        return matcher.matches();

    }

    private boolean emptyFields(){
        boolean checker = true;
        for (int i = 0; i < mainLayout.getChildCount(); i++){
            if (mainLayout.getChildAt(i) instanceof EditText){
                EditText tmp = (EditText) mainLayout.getChildAt(i);
                String forTest = tmp.getText().toString();
                if (forTest.isEmpty()){
                    checker = false;
                    //tmp.setBackgroundResource(R.drawable.red_rectangle);
                    tmp.setBackground(ContextCompat.getDrawable(this, R.drawable.red_rectangle));
                }else{
                    //tmp.setBackgroundResource(android.R.color.transparent);
                    tmp.setBackground(null);
                }
            }
        }
        return checker;
    }

    private String validateFields(String s, EditText et){
        String validS;
        try{
            validS = new String (s.getBytes("UTF-8"), "ISO-8859-1");
            et.setBackground(null);
        } catch (UnsupportedEncodingException e) {
            Toast toast = Toast.makeText(this, "Вы ввели некорректные символы",
                    Toast.LENGTH_LONG);
            toast.show();
            et.setBackground(ContextCompat.getDrawable(this, R.drawable.red_rectangle));
            return "";
        }
        return validS;
    }

    public void onClick(View v){
        if(!emptyFields()){
            return;
        }

        email_ = email.getText().toString();
        String surname_ = surname.getText().toString();
        String name_ = name.getText().toString();
        String batyaName_ = batyaName.getText().toString();
        String day_ = day.getText().toString();
        String month_ = month.getText().toString();
        String year_ = year.getText().toString();
        passport_ = passport.getText().toString();

        if(!isEmailValid(email_)){
            Toast toast = Toast.makeText(this, "Вы ввели некорректную почту",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        //Махинации с переменными для валидности
        finalSurname = validateFields(surname_, surname);
        finalName = validateFields(name_, name);
        finalBatyaName = validateFields(batyaName_, batyaName);

        if(finalSurname.isEmpty() || finalName.isEmpty() || finalBatyaName.isEmpty()){
            return;
        }


        if (!isDateValid(day_, month_, year_)){
            Toast toast = Toast.makeText(this, "Вы ввели некорректную дату",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        //Это для 1С, там первый месяц(день) это 01, а не 1
        day_ = (day_.length() == 2) ? day_ : "0" + day_;
        month_ = (month_.length() == 2) ? month_ : "0" + month_;

        birthday_ = year_ + month_ + day_;

        Requester requester = new Requester();
        requester.execute();
        
    }

    class Requester extends AsyncTask<Void, Void, Void>{
        protected Void doInBackground(Void ...voids){
            String ans = RequestSender.verification(VerificationActivity.this, email_,
                    finalName, finalSurname, finalBatyaName, birthday_, passport_);

            try {
                JSONObject jsonObject = new JSONObject(ans);
                System.out.println(jsonObject.getString("comment"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(ans);
            return null;
        }

        protected Void onPostExecute(Void ...voids){

            return null;
        }
    }

}