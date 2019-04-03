package com.huang.homan.androidtv.Dagger.Module;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.huang.homan.androidtv.R;
import com.huang.homan.androidtv.View.Activity.MainActivity;

import androidx.leanback.app.BackgroundManager;
import dagger.Module;
import dagger.Provides;

@Module
public class BackgroundManagerModule {

    @Provides
    public BackgroundManager provideBackgroundManager(Activity activity) {

        // mBackgroundManager = BackgroundManager
        //     .getInstance(getActivity());
        final BackgroundManager backgroundManager =
                BackgroundManager.getInstance(activity);

        // mBackgroundManager.attach(getActivity().getWindow());
        return backgroundManager;
    }

    @Provides
    public DisplayMetrics provideDisplayMetrics(Activity activity) {

        // mMetrics = new DisplayMetrics();
        DisplayMetrics metrics = new DisplayMetrics();

        // getActivity()
        //    .getWindowManager()
        //    .getDefaultDisplay()
        //    .getMetrics(mMetrics);
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    @Provides
    public Drawable provideDefaultBackground(Activity activity) {
        // mDefaultBackground = ContextCompat
        //   .getDrawable(getActivity(), R.drawable.default_background);
        Drawable defaultBackground = activity.getResources()
                .getDrawable(R.drawable.default_background, null);
        return defaultBackground;
    }
}

