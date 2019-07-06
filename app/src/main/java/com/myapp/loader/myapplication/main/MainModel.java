package com.myapp.loader.myapplication.main;

import com.myapp.loader.myapplication.ArticlesAPI;
import com.myapp.loader.myapplication.networkModels.Article;

import java.util.List;

import rx.Observable;

public class MainModel {

   private ArticlesAPI articleAPI;

    public MainModel(ArticlesAPI articlesAPI) {
        this.articleAPI = articlesAPI;

    }

    public Observable<List<Article>> getArticles(int page) {

        return articleAPI.getArticles(page).map(articleModel -> articleModel.articles);


    }

}
