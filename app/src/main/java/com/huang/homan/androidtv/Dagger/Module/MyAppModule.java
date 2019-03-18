package com.huang.homan.androidtv.Dagger.Module;

import com.huang.homan.androidtv.View.Activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        ActivityBuilderModule.class} )
public abstract class MyAppModule {
}