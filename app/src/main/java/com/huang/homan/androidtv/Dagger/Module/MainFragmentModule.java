package com.huang.homan.androidtv.Dagger.Module;

import com.huang.homan.androidtv.Dagger.Scope.PerFragment;
import com.huang.homan.androidtv.View.Fragment.MainFragment;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        BackgroundManagerModule.class,
        LoadRowsModule.class} )
abstract
class MainFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = MainFragmentUIModule.class)
    abstract MainFragment mainFragment();
}

