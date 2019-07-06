package com.myapp.loader.myapplication.di.components;

import com.myapp.loader.myapplication.di.modules.AppModules;
import com.myapp.loader.myapplication.MyApp;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModules.class})
@Singleton
public interface AppComponent {

    public void inject(MyApp myApp);


}
