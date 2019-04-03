package com.huang.homan.androidtv.Dagger.Module;

import com.huang.homan.androidtv.Dagger.Scope.PerActivity;
import com.huang.homan.androidtv.View.Activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            MainFragmentModule.class})
    abstract MainActivity mainActivity();

}
