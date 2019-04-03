package com.huang.homan.androidtv.View.Activity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class,
            false,
            true);

    @Test
    public void onCreate() {
        MainActivity mainActivity = activityRule.getActivity();
        assertNotNull(mainActivity.fragmentInjector);
        assertNotNull(mainActivity.networkApi);
    }
}