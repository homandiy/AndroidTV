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
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.huang.homan.androidtv.Model.NetworkApi;
import com.huang.homan.androidtv.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    /* Log tag and shortcut */
    final static String TAG = "MYLOG "+MainActivity.class.getSimpleName();
    public static void ltag(String message) { Log.i(TAG, message); }

    @Inject
    NetworkApi networkApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkTouchScreen();
        checkCamera();

        boolean injected = networkApi != null;
        TextView userAvailable = (TextView) findViewById(R.id.target);
        userAvailable.setText(TAG+":\nDependency injection\nworked? " + String.valueOf(injected));
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
}
