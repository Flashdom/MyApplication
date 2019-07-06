package com.myapp.loader.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.loader.myapplication.networkModels.Article;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHoldler> {

    private List<Article> articles;
    private OnItemClick onItemClick;


    public RecyclerViewAdapter() {
        super();
        this.articles = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHoldler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHoldler(root);
    }


    public void addArticles(List<Article> articles) {

        this.articles.addAll(articles);

    }

    public void deleteArticles() {

        this.articles.clear();


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHoldler viewHolder, int i) {
        Article article = articles.get(i);
        viewHolder.titleView.setText(article.title);
        viewHolder.descriptionView.setText(article.description);
        String startDateString = article.publishedAt;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(article.url);
            }
        });
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate;
        try {
            startDate = df.parse(startDateString);
            String newDateString = df2.format(startDate);
            viewHolder.createdAtView.setText(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get().load(article.urlToImage).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public class ViewHoldler extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView titleView, descriptionView, createdAtView;

        public ViewHoldler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.description);
            createdAtView = itemView.findViewById(R.id.date);


        }
    }

}

