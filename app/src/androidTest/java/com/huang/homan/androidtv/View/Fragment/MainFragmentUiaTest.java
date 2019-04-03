package com.huang.homan.androidtv.View.Fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.huang.homan.androidtv.Data.MyHeaderList;
import com.huang.homan.androidtv.View.Activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;


import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.uiautomator.Direction.DOWN;
import static androidx.test.uiautomator.Direction.LEFT;
import static androidx.test.uiautomator.Direction.RIGHT;
import static androidx.test.uiautomator.Direction.UP;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainFragmentUiaTest {

    /* Log tag and shortcut */
    final static String TAG = "MYLOG "+MainFragmentUiaTest.class.getSimpleName();
    public static void ltag(String message) { Log.i(TAG, message); }

    private static final String PackageName
            = "com.huang.homan.androidtv";

    private static final String MainActivityName
            = ".View.Activity.MainActivity";

    private static final int LAUNCH_TIMEOUT = 5000; // 5s

    private UiDevice mTV; // this Android TV
    private PackageManager packageManager;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mTV = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mTV.pressHome();

        // Wait for launcher
        final String launcherPackage = mTV.getLauncherPackageName();
        ltag("pkg: "+launcherPackage);
        assertThat(launcherPackage, notNullValue());
        mTV.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        assertThat(context, notNullValue());

        packageManager = context.getPackageManager();
        assertThat(packageManager, notNullValue());

        /*
        final Intent intent = packageManager
                .getLaunchIntentForPackage(PackageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        */
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                PackageName,
                PackageName+MainActivityName));

        ltag("Loading Activity.");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mTV.wait(Until.hasObject(By.pkg(PackageName).depth(0)), LAUNCH_TIMEOUT);

    }

    // Check Android TV has turned on.
    @Test
    public void checkPreconditions() {
        assertThat(mTV, notNullValue());
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    @Test
    public void moveToAllObj() throws UiObjectNotFoundException {
        checkPreconditions();

        for (int i=0; i<MyHeaderList.HEADER_CATEGORY.length; i++) {
            pressDpad(RIGHT);
            trySomeItems(3);
            pressDpad(DOWN);
        }
    }

    public void trySomeItems(int count) throws UiObjectNotFoundException {
        for (int i = 0; i < count; i++) {
            mClick();
            mBack();
            pressDpad(RIGHT);
        }
        mBack();
    }

    public void pressDpad(Direction direction) {
        switch (direction) {
            case UP:
                mTV.pressDPadUp();
                break;
            case DOWN:
                mTV.pressDPadDown();
                break;
            case LEFT:
                mTV.pressDPadLeft();
                break;
            case RIGHT:
                mTV.pressDPadRight();
                break;
            default:
                throw new IllegalArgumentException(direction.toString());
        }
    }

    public void mBack() {
        mTV.pressBack();
    }

    public void mClick() throws UiObjectNotFoundException {
        // click
        UiObject uiObject = mTV.findObject(new UiSelector().focused(true));
        assertThat(uiObject, notNullValue());
        uiObject.click();
    }

}
