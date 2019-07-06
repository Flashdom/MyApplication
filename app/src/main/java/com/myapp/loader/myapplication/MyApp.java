package com.myapp.loader.myapplication;

import android.app.Application;

import com.myapp.loader.myapplication.di.components.AppComponent;
import com.myapp.loader.myapplication.di.components.DaggerAppComponent;
import com.myapp.loader.myapplication.di.modules.AppModules;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {

    @Inject
    ArticlesAPI articlesAPI;
    public static AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
        initializeDagger();
        appComponent.inject(this);

    }

    private void initializeDagger() {
        appComponent = DaggerAppComponent.builder().appModules(new AppModules()).build();


    }
}
