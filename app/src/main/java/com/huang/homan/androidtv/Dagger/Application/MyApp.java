package com.huang.homan.androidtv.Dagger.Application;

import android.app.Activity;
import android.app.Application;

import com.huang.homan.androidtv.Dagger.Component.DaggerMyAppComponent;
import com.huang.homan.androidtv.Dagger.Scope.AppDependency;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

public class MyApp extends Application implements HasActivityInjector {

    @Inject
    AppDependency appDependency;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerMyAppComponent.create().inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

}
