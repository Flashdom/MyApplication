package com.myapp.loader.myapplication.main.di;

import com.myapp.loader.myapplication.di.modules.AppModules;
import com.myapp.loader.myapplication.main.MainActivity;
import com.myapp.loader.myapplication.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class, AppModules.class})
@Singleton
public interface MainComponent {

    public void inject(MainActivity mainActivity);

    public MainPresenter providePresenter();

}
