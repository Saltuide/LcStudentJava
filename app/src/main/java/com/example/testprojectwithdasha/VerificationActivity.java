package com.example.testprojectwithdasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
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
import java.util.Timer;
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

        email_ =  MainActivity.sPref.getString("e_mail", "");
        email.setText(email_);
        email.setInputType(InputType.TYPE_NULL); // readonly

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
                    tmp.setBackground(ContextCompat.getDrawable(this, R.drawable.red_rectangle));
                }else{
                    tmp.setBackground(ContextCompat.getDrawable(this, R.color.help_back));
                }
            }
        }
        return checker;
    }

    private String validateFields(String s, EditText et){
        String validS;
        try{
            validS = new String (s.getBytes("UTF-8"), "ISO-8859-1");
            et.setBackground(ContextCompat.getDrawable(this, R.color.help_back));
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
            Toast toast = Toast.makeText(this, "Вы заполнили не все поля",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        String surname_ = surname.getText().toString();
        String name_ = name.getText().toString();
        String batyaName_ = batyaName.getText().toString();
        String day_ = day.getText().toString();
        String month_ = month.getText().toString();
        String year_ = year.getText().toString();
        passport_ = passport.getText().toString();


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
        boolean checker = true, checkStatus = true;
        String comment;
        protected Void doInBackground(Void ...voids){
            String ans = RequestSender.verification(VerificationActivity.this, email_,
                    finalName, finalSurname, finalBatyaName, birthday_, passport_);

            System.out.println(ans);
            try {
                JSONObject jsonObject = new JSONObject(ans);
                boolean status = jsonObject.getBoolean("status");
                if(!status){
                    checkStatus = false;
                    comment = jsonObject.getString("comment");
                    return null;
                }
                SharedPreferences.Editor ed = MainActivity.sPref.edit();
                ed.putBoolean("is_verificated", true);
                ed.putString("last_name", jsonObject.getString("last_name"));
                ed.putString("first_name", jsonObject.getString("first_name"));
                ed.putString("middle_name", jsonObject.getString("middle_name"));
                ed.commit();
            } catch (JSONException e) {
                checker = false;
            }

            return null;
        }

        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            if(!checkStatus){
                Toast toast = Toast.makeText(VerificationActivity.this, comment,
                        Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            if (!checker){
                Toast toast = Toast.makeText(VerificationActivity.this,
                        "Не удалось установить соединение с сервером", Toast.LENGTH_LONG);
                toast.show();
            }
            Intent intent = new Intent(VerificationActivity.this, MenuActivity.class);
            intent.putExtra("activity", "verification");
            startActivity(intent);
            VerificationActivity.this.finish();

            return;
        }
    }

}