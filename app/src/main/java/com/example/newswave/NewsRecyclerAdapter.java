package com.example.newswave;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{
    List<Article> articleList;
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row,parent,false);
    return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.baseline_hide_image_24)
                .placeholder(R.drawable.baseline_hide_image_24)
                .resize(400,400)
                .centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener((view -> {
            Intent intent = new Intent(view.getContext(),NewsFullActivity.class);
            intent.putExtra("url",article.getUrl());
            view.getContext().startActivity(intent);
        }));


    }
    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }
    // Configuring Java file with xml of main activiy
    class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,sourceTextView;
        ImageView  imageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);
        }
    }
}
