package com.huang.homan.androidtv.Dagger.Module;

import android.app.Activity;

import com.huang.homan.androidtv.Dagger.Scope.PerActivity;
import com.huang.homan.androidtv.View.Activity.MainActivity;
import com.huang.homan.androidtv.View.Fragment.MainFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public class MainActivityModule {

    @Provides
    @PerActivity
    Activity provideMainActivityModule(MainActivity mainActivity) {
        return mainActivity;
    }
}

