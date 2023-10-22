package com.example.newswave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;

    Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7;
    LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);


        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        setupRecyclerView();
        getNews("GENERAL");
    }

    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }


    void changeInProgress(boolean show) {
        if (show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    private void getNews(String category) {
        if (category=="GENERAL"){
            btn_1.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        }
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("ee9adb7f38c449a8999135ccba740b93");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .category(category)
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(() -> {
                            changeInProgress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Failed To Got Respones", throwable.getMessage());
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String category = btn.getText().toString();

        // Reset the color of all buttons to the default color
        resetButtonColors();

        // Highlight the clicked button
        btn.setBackgroundColor(getResources().getColor(R.color.default_button_color));

        // Fetch news for the selected category
        getNews(category);
    }

    private void resetButtonColors() {
        btn_1.setBackgroundColor(getResources().getColor(R.color.my_primary));
        btn_2.setBackgroundColor(getResources().getColor(R.color.my_primary));
        btn_3.setBackgroundColor(getResources().getColor(R.color.my_primary));
        btn_4.setBackgroundColor(getResources().getColor(R.color.my_primary));
        btn_5.setBackgroundColor(getResources().getColor(R.color.my_primary));
        btn_6.setBackgroundColor(getResources().getColor(R.color.my_primary));
        btn_7.setBackgroundColor(getResources().getColor(R.color.my_primary));
    }

}
