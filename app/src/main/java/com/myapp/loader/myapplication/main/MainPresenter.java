package com.myapp.loader.myapplication.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.myapp.loader.myapplication.ArticlesData;
import com.myapp.loader.myapplication.ArticlesDataToArticleConverter;
import com.myapp.loader.myapplication.networkModels.Article;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<IMainView> {

    ArticlesDataToArticleConverter converter;
    private Realm realm;
    private ArrayList<Article> list = new ArrayList<>();
    public MainModel model;
    private boolean showArticles = false;

    public MainPresenter(MainModel model, Realm realm) {
        this.model = model;
        this.realm = realm;
    }

    public void loadArticles(int page, boolean isNetworkConnected) {
        if (isNetworkConnected) {
            clearScreen();
            model.getArticles(page)
                    .subscribeOn(Schedulers.newThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            getViewState().showLoadIndicator();
                        }
                    }).doOnNext(new Action1<List<Article>>() {
                @Override
                public void call(List<Article> articles) {
                    getViewState().hideLoadIndiactor();
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Article>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            getViewState().showError(e);
                        }

                        @Override
                        public void onNext(List<Article> articles) {
                            list.addAll(articles);

                            realm.beginTransaction();
                            for (int i = 0; i < articles.size(); i++) {
                                ArticlesData articlesData = realm.createObject(ArticlesData.class);
                                articlesData.description = articles.get(i).description;
                                articlesData.urlToImage = articles.get(i).urlToImage;
                                articlesData.title = articles.get(i).title;
                                articlesData.publishedAt = articles.get(i).publishedAt;
                            }
                            realm.commitTransaction();
                            getViewState().showArticles(articles);

                        }
                    });

        } else {
            RealmResults<ArticlesData> managedArticlesDataset = realm.where(ArticlesData.class).findAll();
            List<ArticlesData> articleDataSet = realm.copyFromRealm(managedArticlesDataset);
            converter = new ArticlesDataToArticleConverter();
            if (!showArticles)
                getViewState().showArticles(converter.convert(articleDataSet));
            Throwable throwable = new Exception();
            getViewState().showError(throwable);
            showArticles = true;

        }
    }


    public void closeDB() {
        realm.close();
    }


    private void clearScreen() {

        if (showArticles) {
            RealmResults<ArticlesData> managedArticlesDataset = realm.where(ArticlesData.class).findAll();
            getViewState().deleteArticles();
            realm.executeTransaction(realm -> managedArticlesDataset.deleteAllFromRealm());
            showArticles = false;
        }

    }


}






