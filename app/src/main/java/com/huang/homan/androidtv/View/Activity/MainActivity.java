/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.huang.homan.androidtv.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huang.homan.androidtv.Dagger.Scope.ActivityDependency;
import com.huang.homan.androidtv.Dagger.Scope.AppDependency;
import com.huang.homan.androidtv.Helper.BaseActivityVP;
import com.huang.homan.androidtv.Model.NetworkApi;
import com.huang.homan.androidtv.Presenter.MsgPresenter;
import com.huang.homan.androidtv.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

/*
 * Main Activity class that loads {@link YouTubeMainFragment}.
 */
public class MainActivity extends Activity
        implements HasFragmentInjector, BaseActivityVP.View {

    /* Log tag and shortcut */
    final static String TAG = "MYLOG "+MainActivity.class.getSimpleName();
    public static void ltag(String message) { Log.i(TAG, message); }

    TextView diAvailable;

    MsgPresenter presenter;
    public MsgPresenter getPresenter() {
        return presenter;
    }

    @Inject
    NetworkApi networkApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ltag("onCreate.");

        // Dagger Injector
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Message Presenter
        presenter = new MsgPresenter(this);
        diAvailable = findViewById(R.id.target);
        diAvailable.setVisibility(View.INVISIBLE);

        // Check hardware
        checkTouchScreen();
        checkCamera();

        // Test networkApi on screen
        boolean injected = networkApi != null;
        ltag(
                TAG+":\nDependency injection\nworked? " +
                        String.valueOf(injected));
    }

    private void checkTouchScreen() {
        if (getPackageManager().hasSystemFeature("android.hardware.touchscreen")) {
            ltag("HardwareFeatureTest: Touch screen is available.");
        } else {
            ltag("HardwareFeatureTest: NO Touch screen!");
        }
    }

    private void checkCamera() {
        if (getPackageManager().hasSystemFeature("android.hardware.camera2")) {
            ltag("HardwareFeatureTest: Camera is available.");
        } else {
            ltag("HardwareFeatureTest: No camera! View and edit features only.");
        }
    }


    @Inject
    AppDependency appDependency; // same object from App

    @Inject
    ActivityDependency activityDependency;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void showMessage(String msg) {
        diAvailable.setVisibility(View.VISIBLE);
        diAvailable.setText(msg);
    }
}
