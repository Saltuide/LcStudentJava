package com.example.testprojectwithdasha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testprojectwithdasha.adapters.NewsAdapter;
import com.example.testprojectwithdasha.classes.News;
import com.example.testprojectwithdasha.classes.RecyclerItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity{

    List<News> news = new ArrayList<>();
    SetData setData;
    private Button navigGoToRasp;
    private Button navigGoToNews;
    private Button navigGoToMenu;
    private NewsFragment currentNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setData = new SetData();
        setData.execute();
    }

    @Override
    public void onBackPressed() {
        // Выход в главное меню, если при открытии активити новостей, мы не открывали ни одну
        // новость целиком - переменная остается неопределенной и все падает
        if (currentNews == null){
            super.onBackPressed();
            return;
        }
        if(currentNews.isVisible()){
            currentNews.backButtonWasPressed();
            //делает меню обратно видимым
            Button goToRasp = findViewById(R.id.goto_rasp);
            goToRasp.setVisibility(View.VISIBLE);
            Button goToMenu = findViewById(R.id.goto_menu);
            goToMenu.setVisibility(View.VISIBLE);
            Button goToNews = findViewById(R.id.goto_news);
            goToNews.setVisibility(View.VISIBLE);
        }else {
            super.onBackPressed();
        }
    }

    class SetData extends AsyncTask<Void, Void, Void>{

        private String errorMessage = "";
        private String fullText;
        @Override
        protected Void doInBackground(Void... voids){
            String title, tag, pubDate, description, mainImageUrl;
            Bitmap mainImage;

            String tmp = RequestSender.getNews(NewsActivity.this, "default",
                        "all", "all", "all", 1);
            JSONObject answer;
            try {
                answer = new JSONObject(tmp);
            } catch (JSONException e) {
                System.out.println(tmp);
                errorMessage = "Ошибка при подключении к серверу";
                return null;
            }
            try {
                if (answer.getString("status") == "false"){
                    errorMessage = "Неизвестная ошибка (мы сами не знаем, как так вышло)";
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject body = answer.getJSONObject("news_array");
                for (int i = 0; i < body.names().length(); i++) {
                    String key = body.names().getString(i);
                    JSONObject item = body.getJSONObject(key);
                    title = item.getString("news_title");
                    tag = item.getString("news_tag");
                    pubDate = item.getString("news_pub_date");
                    description = item.getString("news_short_text");
                    mainImageUrl = item.getString("news_main_img");
                    fullText = item.getString("news_main_text");
                    InputStream in = new java.net.URL(mainImageUrl).openStream();
                    mainImage = BitmapFactory.decodeStream(in);
                    news.add(new News(title, tag, pubDate, description, mainImage));
                }
            } catch (JSONException | MalformedURLException e) {
                errorMessage = "Неизвестная ошибка #2 (мы сами не знаем, как так вышло)";
                e.printStackTrace();
            } catch (IOException e) {
                errorMessage = "Неизвестная ошибка #2 (мы сами не знаем, как так вышло)";
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView = findViewById(R.id.list);
            NewsAdapter adapter = new NewsAdapter(NewsActivity.this, news);

            recyclerView.setAdapter(adapter);
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(NewsActivity.this, recyclerView ,
                            new RecyclerItemClickListener.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {
                            FragmentManager fm = getSupportFragmentManager();
                            currentNews = new NewsFragment(fullText);
                            fm.beginTransaction().replace(R.id.newsMainLayout, currentNews).commit();

                            //Скрываем нижнюю панель (почему нет общего серого фона, я хз, он сам пропадает _-_)
                            Button goToRasp = findViewById(R.id.goto_rasp);
                            goToRasp.setVisibility(View.INVISIBLE);
                            Button goToMenu = findViewById(R.id.goto_menu);
                            goToMenu.setVisibility(View.INVISIBLE);
                            Button goToNews = findViewById(R.id.goto_news);
                            goToNews.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    })
            );

            navigGoToRasp = (Button) findViewById(R.id.goto_rasp);
            navigGoToRasp.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        navigGoToRasp.setBackgroundResource(R.drawable.rasp_grey);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        navigGoToRasp.setBackgroundResource(R.drawable.rasp_white);
                        //open_RaspActivity();
                    }

                    return true;
                }
            });

            navigGoToNews = (Button) findViewById(R.id.goto_news);
            navigGoToNews.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        navigGoToNews.setBackgroundResource(R.drawable.news_darkgreen);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        navigGoToNews.setBackgroundResource(R.drawable.news_green);
                    }

                    return true;
                }
            });

            navigGoToMenu = (Button) findViewById(R.id.goto_menu);
            navigGoToMenu.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        navigGoToMenu.setBackgroundResource(R.drawable.menu_grey);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        navigGoToMenu.setBackgroundResource(R.drawable.menu_white);
                        open_MenuActivity();
                    }

                    return true;
                }
            });

            if (!errorMessage.isEmpty()){
                Toast toast = Toast.makeText(NewsActivity.this, errorMessage,
                        Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    /*public void open_RaspActivity() {
        Intent intent = new Intent(this, RaspActivity.class);
        startActivity(intent);
    }*/

    public void open_MenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
