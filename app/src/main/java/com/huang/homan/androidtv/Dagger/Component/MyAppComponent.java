package com.huang.homan.androidtv.Dagger.Component;

import com.huang.homan.androidtv.Dagger.Application.MyApp;
import com.huang.homan.androidtv.Dagger.Module.ActivityBuildersModule;
import com.huang.homan.androidtv.Dagger.Module.FragmentBuilderModule;
import com.huang.homan.androidtv.Dagger.Module.MyAppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        MyAppModule.class,
        ActivityBuildersModule.class,})
public interface MyAppComponent extends AndroidInjector<MyApp> {

}



