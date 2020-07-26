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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.example.testprojectwithdasha.adapters.NewsAdapter;
import com.example.testprojectwithdasha.classes.News;
import com.example.testprojectwithdasha.classes.RecyclerItemClickListener;
import com.example.testprojectwithdasha.fragments.NewsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


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
            ConstraintLayout newsNavigLayout = findViewById(R.id.navigBtnContainerNews);
            newsNavigLayout.setVisibility(View.VISIBLE);
        }else {
            super.onBackPressed();
        }
    }

    class SetData extends AsyncTask<Void, Void, Void>{

        private String errorMessage = "";


        @SuppressLint("ClickableViewAccessibility")
        protected void onPreExecute(){
            super.onPreExecute();
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
            return;
        };

        @Override
        protected Void doInBackground(Void... voids){
            String title, tag, pubDate, description, mainImageUrl;
            Bitmap mainImage;
            JSONArray otherImages;
            String fullText;


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
                errorMessage = "Неизвестная ошибка (мы сами не знаем, как так вышло)";
                return null;
            }

            JSONObject body;
            try {
                body = answer.getJSONObject("news_array");
            } catch (JSONException e) {
                errorMessage = "Неизвестный ответ сервера";
                return null;
            }

            for (int i = 0; i < body.names().length(); i++) {
                try {
                    String key = body.names().getString(i);
                    JSONObject item = body.getJSONObject(key);
                    title = item.getString("news_title");
                    tag = item.getString("news_tag");
                    pubDate = item.getString("news_pub_date");
                    description = item.getString("news_short_text");
                    mainImageUrl = item.getString("news_main_img");
                    fullText = item.getString("news_main_text");
                    otherImages = item.getJSONArray("news_img_gallery");
                } catch (JSONException e) {
                    errorMessage = "Неизвестная ошибка (чо-та с get в цикле)";
                    continue;
                }
                try {

                    FutureTarget<Bitmap> futureTarget =
                            Glide.with(NewsActivity.this)
                                    .asBitmap()
                                    .load(mainImageUrl)
                                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    mainImage = futureTarget.get();
                } catch (InterruptedException e) {
                    mainImage = BitmapFactory.decodeResource(NewsActivity.this.getResources(),
                            R.drawable.error_pic);
                } catch (ExecutionException e) {
                    mainImage = BitmapFactory.decodeResource(NewsActivity.this.getResources(),
                            R.drawable.error_pic);
                }

                    news.add(new News(title, tag, pubDate, description, mainImage, fullText, otherImages));
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
                            NewsImgGalleryThread secondThread = new NewsImgGalleryThread();
                            secondThread.execute(position);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    })
            );

            if (!errorMessage.isEmpty()){
                Toast toast = Toast.makeText(NewsActivity.this, errorMessage,
                        Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
// подгрузка галереи отдельно от главных картинок
    class NewsImgGalleryThread extends AsyncTask<Integer, Void, Void>{
        Bitmap imageFromGallery;
        private int newsPositionToShow;
        private List<Bitmap> galleryList = new ArrayList<>();
        private JSONArray otherImages;


        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Integer... position) {
            newsPositionToShow = position[0];
            otherImages = news.get(newsPositionToShow).getOtherImages();
            for (int j = 0; j < otherImages.length(); j++) {
                String link;
                //Bitmap image;
                try {
                    link = otherImages.getString(j);
                    FutureTarget<Bitmap> futureTarget =
                            Glide.with(NewsActivity.this)
                                    .asBitmap()
                                    .load(link)
                                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    imageFromGallery = futureTarget.get();
                    galleryList.add(imageFromGallery);
                } catch (InterruptedException e) {
//                    errorMessage = "Доступ к информационному ресурсу огрничен " +
//                            "распоряжением РКН от 23.19.2016 № 325.27.2";
                    e.printStackTrace();
                } catch (ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            FragmentManager fm = getSupportFragmentManager();
            //Получаем нужные параметры для показала новости целиком
            String fullText = news.get(newsPositionToShow).getFullText();

            currentNews = new NewsFragment(fullText, galleryList);
            fm.beginTransaction().replace(R.id.newsLayout, currentNews).commit();

            //Скрываем нижнюю панель (почему нет общего серого фона, я хз, он сам пропадает _-_)
            ConstraintLayout newsNavigLayout = findViewById(R.id.navigBtnContainerNews);
            newsNavigLayout.setVisibility(View.INVISIBLE);
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
