package com.myapp.loader.myapplication;

import com.myapp.loader.myapplication.networkModels.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticlesDataToArticleConverter {

    private ArrayList<Article> endList;

    public ArrayList<Article> convert(List<ArticlesData> articlesData) {
        endList = new ArrayList<Article>();

        for (int i = 0; i < articlesData.size(); i++) {

            endList.add(new Article());

        }


        for (int i = 0; i < articlesData.size(); i++) {

            endList.get(i).title = articlesData.get(i).title;
            endList.get(i).description = articlesData.get(i).description;
            endList.get(i).urlToImage = articlesData.get(i).urlToImage;
            endList.get(i).publishedAt = articlesData.get(i).publishedAt;

        }


        return endList;
    }
}
