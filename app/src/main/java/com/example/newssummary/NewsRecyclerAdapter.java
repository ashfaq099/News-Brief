
package com.example.newssummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newssummary.Models.NewsHeadlines;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>   {

    private List<NewsHeadlines> headlines;
    private Selectlistener listener;
    private Context context;
    List<Article> articleList;

    public NewsRecyclerAdapter(Context context, List<NewsHeadlines> headlines, Selectlistener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    // Add this constructor for the case when using it for Articles
    public NewsRecyclerAdapter(List<Article> articleList, Selectlistener listener) {
        this.articleList = articleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.no_image_icon)
                .placeholder(R.drawable.no_image_icon)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass the corresponding NewsHeadlines instance
                listener.OnNewsClicked(convertToNewsHeadlines(article));
            }
        });
    }

    private NewsHeadlines convertToNewsHeadlines(Article article) {
        // Convert Article to NewsHeadlines
        NewsHeadlines newsHeadlines = new NewsHeadlines(
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getSource().getName()
        );
        return newsHeadlines;
    }



void updateData(List<Article> data) {
    if (articleList == null) {
        articleList = new ArrayList<>(); // Initialize the list if it's null
    }
    articleList.clear();
    articleList.addAll(data);
    notifyDataSetChanged();
}

    @Override
    public int getItemCount() {

            if (articleList == null) {
                return 0; // or return a default size if needed
            }
            return articleList.size();
        }



    static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView, sourceTextView;
        ImageView imageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);
            cardView = itemView.findViewById(R.id.main_container);
        }
    }
}
