package com.example.testprojectwithdasha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.example.testprojectwithdasha.adapters.NewsAdapter;
import com.example.testprojectwithdasha.adapters.SpinnerSelectorAdapter;
import com.example.testprojectwithdasha.classes.News;
import com.example.testprojectwithdasha.classes.RecyclerItemClickListener;
import com.example.testprojectwithdasha.classes.SelectorsModel;
import com.example.testprojectwithdasha.fragments.NewsFragment;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class NewsActivity extends AppCompatActivity{

    private List<News> news = new ArrayList<>();
    private Menu menu;
    private SetData setData;
    private Button navigGoToRasp, navigGoToNews, navigGoToMenu, btn;
    private NewsFragment currentNews;
    private RecyclerView mRecyclerView;
    private SpinnerSelectorAdapter yearSelectorAdapter, monthSelectorAdapter, tagsSelectorAdapter;
    private List<SelectorsModel> mModelList;
    private Spinner monthsSpinner, yearSpinner, tagsSpinner;
    private int filterUsageCounter, currentYear;
    private Calendar calendar;

    private ArrayList<String> selectedMonthTags = new ArrayList<String>();//1-12
    private ArrayList<String> selectedYearTags = new ArrayList<String>(); //2015-...
    private ArrayList<String> selectedNewsTags = new ArrayList<String>(); //"someStrings"

    private JSONArray tagList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

//        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
//                    isShow = true;
//                    showOption(R.id.action_info);
                } else if (isShow) {
//                    isShow = false;
//                    hideOption(R.id.action_info);
                }
            }
        });

        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SelectorsModel> monthModel = monthSelectorAdapter.getModel();
                ArrayList<SelectorsModel> yearModel = yearSelectorAdapter.getModel();
                ArrayList<SelectorsModel> tagsModel = tagsSelectorAdapter.getModel();
                //очищаем, чтобы результат нажатий на кнопку "ПРИМЕНИТЬ" и выбранных фильтров
                //не накладывался сам на себя
                selectedMonthTags.clear();
                selectedYearTags.clear();
                selectedNewsTags.clear();

                for (int i = 0; i < yearModel.size(); i++) {
                    if(yearModel.get(i).isSelected()){
                        System.out.println("Выбрано : " + yearModel.get(i).getText());
                        selectedYearTags.add(yearModel.get(i).getText());
                    }
                }
                for (int i = 0; i < monthModel.size(); i++) {
                    if(monthModel.get(i).isSelected()){
                        System.out.println("Выбрано : " + monthModel.get(i).getText());
                        selectedMonthTags.add(Integer.toString(i));
                    }
                }
                for (int i = 0; i < tagsModel.size(); i++) {
                    if(tagsModel.get(i).isSelected()){
                        System.out.println("Выбрано : " + tagsModel.get(i).getText());
                        selectedNewsTags.add(tagsModel.get(i).getText());
                    }
                }
                SetData s1 = new SetData();
                s1.execute();
            }
        });


        setData = new SetData();
        setData.execute();
    }

    private ArrayList<SelectorsModel> getListData(String type) {
        mModelList = new ArrayList<>();
        ArrayList<String> att = new ArrayList<>();
        switch (type){
            case "years":
                att.add("Год");
                for(int i = 2015; i <= currentYear; i++){
                    att.add(String.valueOf(i));
                }
                break;

            case "months":
                att.addAll(Arrays.asList(getResources().getStringArray(R.array.months)));
                break;
            case "tags":
                att.add("Тег");
                for(int i = 0; i < tagList.length(); i++){
                    try {
                        att.add(tagList.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                break;
        }
        for (int i = 0; i < att.size(); i++) {
            mModelList.add(new SelectorsModel(att.get(i)));
        };
        return (ArrayList<SelectorsModel>) mModelList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        } else if (id == R.id.action_info) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
//        MenuItem item = menu.findItem(id);
//        item.setVisible(false);
    }

    private void showOption(int id) {
//        MenuItem item = menu.findItem(id);
//        item.setVisible(true);
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
            AppBarLayout appBarLayout = findViewById(R.id.app_bar);
            appBarLayout.setVisibility(View.VISIBLE);
        }else {
            super.onBackPressed();
        }
    }

    class SetData extends AsyncTask<Void, Void, Void>{

        public void selfRestart() {
            setData = new SetData();
        }

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
            String request = null;

            if(selectedYearTags.isEmpty()){
                selectedYearTags.add("all");
                System.out.println("year is empty" + " " + selectedYearTags.get(0) + ", size :" + selectedYearTags.size());
            }

            if(selectedMonthTags.isEmpty()){
                selectedMonthTags.add("all");
                System.out.println("month is empty" + " " + selectedMonthTags.get(0) + ", size :" + selectedMonthTags.size());
            }
            if(selectedNewsTags.isEmpty()){
                selectedNewsTags.add("all");
                System.out.println("tag is empty" + " " + selectedNewsTags.get(0) + ", size :" + selectedNewsTags.size());
            }

            news.clear();
            try {
                request = RequestSender.getNews(NewsActivity.this, selectedYearTags,
                        selectedMonthTags, selectedNewsTags, 1);
            } catch (JSONException e) {
                // TODO: 13.08.2020 change the exception
                e.printStackTrace();
            }

            JSONObject answer;


            try {
                answer = new JSONObject(request);
            } catch (JSONException e) {
                System.out.println(request);
                errorMessage = "Ошибка при подключении к серверу";
                return null;
            }

            try {
                if (answer.getString("status") == "false"){
                    errorMessage = "Неизвестная ошибка (мы сами не знаем, как так вышло #1)";
                    return null;
                }
            } catch (JSONException e) {
                errorMessage = "Неизвестная ошибка (мы сами не знаем, как так вышло #2)";
                return null;
            }

            JSONObject body;
            try {
                body = answer.getJSONObject("news_array");
            } catch (JSONException e) {
                errorMessage = "Неизвестный ответ сервера #1";
                return null;
            }

            try {
                tagList = answer.getJSONArray("tags");
            } catch (JSONException e) {
                errorMessage = "Неизвестный ответ сервера #2";
                return null;
            }

            for (int i = 0; i < body.names().length(); i++) {
                try {
                    String index = body.names().getString(i);
                    JSONObject item = body.getJSONObject(index);
                    title = item.getString("news_title");
                    tag = item.getString("news_tag");
                    pubDate = item.getString("news_pub_date");
                    description = item.getString("news_short_text");
                    mainImageUrl = item.getString("news_main_img");
                    fullText = item.getString("news_main_text");
                    otherImages = item.getJSONArray("news_img_gallery");
                } catch (JSONException e) {
                    errorMessage = "Неизвестная ошибка при получении аттрибута" +
                            " новости из списка новостей";
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

            System.out.println("sdfdgfhgjnhkgjlhkjl,ghmfgndbsvac " + news.size());
            return null;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView recyclerView = findViewById(R.id.list);
            NewsAdapter adapter = new NewsAdapter(NewsActivity.this, news);


            calendar = Calendar.getInstance();
            currentYear = calendar.get(Calendar.YEAR);
            yearSpinner = (Spinner)findViewById(R.id.yearFilterSpinner);
            yearSelectorAdapter = new SpinnerSelectorAdapter(NewsActivity.this, getListData("years"));
            yearSpinner.setAdapter(yearSelectorAdapter);

            monthsSpinner = (Spinner)findViewById(R.id.monthFilterSpinner);
            monthSelectorAdapter = new SpinnerSelectorAdapter(NewsActivity.this, getListData("months"));
            monthsSpinner.setAdapter(monthSelectorAdapter);

            tagsSpinner = (Spinner)findViewById(R.id.tagFilterSpinner);
            tagsSelectorAdapter = new SpinnerSelectorAdapter(NewsActivity.this, getListData("tags"));
            tagsSpinner.setAdapter(tagsSelectorAdapter);


            recyclerView.setAdapter(adapter);
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(NewsActivity.this, recyclerView ,
                            new RecyclerItemClickListener.OnItemClickListener() {

                                @Override
                                public void onItemClick(View view, int position) {
                                    NewsImgGalleryThread secondThread = new NewsImgGalleryThread();
                                    AppBarLayout appBarLayout = findViewById(R.id.app_bar);
                                    appBarLayout.setVisibility(View.INVISIBLE);
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
