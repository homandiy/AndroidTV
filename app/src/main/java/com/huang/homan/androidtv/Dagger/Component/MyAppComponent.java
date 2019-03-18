package com.huang.homan.androidtv.Dagger.Component;

import com.huang.homan.androidtv.Dagger.Application.MyApp;
import com.huang.homan.androidtv.Dagger.Module.MyAppModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {
        AndroidInjectionModule.class,
        MyAppModule.class})
public interface MyAppComponent extends AndroidInjector<MyApp> {
}