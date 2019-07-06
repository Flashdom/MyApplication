package com.myapp.loader.myapplication;

import com.myapp.loader.myapplication.networkModels.ArticleModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ArticlesAPI {

        @GET("/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907")
        public Observable<ArticleModel> getArticles(@Query("page") int page);


}
