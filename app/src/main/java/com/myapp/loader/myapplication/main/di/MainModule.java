package com.myapp.loader.myapplication.main.di;

import com.myapp.loader.myapplication.ArticlesAPI;
import com.myapp.loader.myapplication.main.MainModel;
import com.myapp.loader.myapplication.main.MainPresenter;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class MainModule {

    @Provides
    public MainPresenter providePresenter(MainModel model, Realm realm)
    {
        return new MainPresenter(model, realm);

    }

    @Provides
    public MainModel provideMainModel(ArticlesAPI articlesAPI)
    {
        return new MainModel(articlesAPI);

    }

}
