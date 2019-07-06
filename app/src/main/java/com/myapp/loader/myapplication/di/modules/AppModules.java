package com.myapp.loader.myapplication.di.modules;

import com.myapp.loader.myapplication.ArticlesAPI;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModules {

    @Provides
    public ArticlesAPI provideAPI()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ArticlesAPI.class);

    }
    @Provides
    public Realm provideRealm()
    {
        return Realm.getDefaultInstance();

    }


}
