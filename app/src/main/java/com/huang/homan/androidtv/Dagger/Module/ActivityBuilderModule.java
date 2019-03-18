package com.huang.homan.androidtv.Dagger.Module;

import com.huang.homan.androidtv.View.Activity.BrowseErrorActivity;
import com.huang.homan.androidtv.View.Activity.DetailsActivity;
import com.huang.homan.androidtv.View.Activity.MainActivity;
import com.huang.homan.androidtv.View.Activity.PlaybackActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();


}
