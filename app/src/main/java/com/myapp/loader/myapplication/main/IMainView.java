package com.myapp.loader.myapplication.main;

import com.arellomobile.mvp.MvpView;
import com.myapp.loader.myapplication.networkModels.Article;

import java.util.List;

public interface IMainView extends MvpView {

    public void showLoadIndicator();

    public void hideLoadIndiactor();

    public void showError(Throwable throwable);

    public void showArticles(List<Article> articles);


    public void deleteArticles();

}
