package com.example.testprojectwithdasha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity{

    List<News> news = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            setData();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "shit negro",Toast.LENGTH_LONG);
            toast.show();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        NewsAdapter adapter = new NewsAdapter(this, news);

        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setData() throws Exception {
        String title, tag, pubDate, description;
        String tmp = RequestSender.getNews(NewsActivity.this, "default",
                "all","all","all", 1);
        ;
        JSONObject answer = new JSONObject(tmp);
        System.out.println(answer.getString("status"));
        if(answer.getString("status") == "false") return;
        JSONObject body = answer.getJSONObject("news_array");
        for(int i = 0; i < body.names().length(); i++){
            String key = body.names().getString(i);
            JSONObject item = body.getJSONObject(key);
            title = item.getString("news_title");
            tag = item.getString("news_tag");
            pubDate = item.getString("news_pub_date");
            description = item.getString("news_short_text");
            news.add(new News(title, tag, pubDate, description));
        }
    }
}
