package com.example.newswave;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNews();
    }

    private void getNews() {
        NewsApiClient newsApiClient = new NewsApiClient("ee9adb7f38c449a8999135ccba740b93");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        response.getArticles().forEach((a)->{
                            Log.i("Article",a.getTitle());
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Failed To Got Respones",throwable.getMessage());
                    }
                }
        );
    }
}