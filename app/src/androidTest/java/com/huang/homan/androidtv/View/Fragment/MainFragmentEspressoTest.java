package com.huang.homan.androidtv.View.Fragment;


import androidx.fragment.app.FragmentManager;

import com.huang.homan.androidtv.R;
import com.huang.homan.androidtv.View.Activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.fragment.app.Fragment;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import dagger.android.AndroidInjection;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainFragmentEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class,
            false,
            true);

    private MainActivity mainActivity;
    private MainFragment mainFragment;


    @Before
    public void setUp() {
        mainActivity = activityRule.getActivity();

        mainActivity.runOnUiThread(
            new Runnable() {
                public void run() {
                    mainFragment = new MainFragment();
                    mainActivity.getFragmentManager()
                            .beginTransaction()
                            .add(R.id.mainFL, mainFragment)
                            .commit();

            }
        });
    }

    @Test
    public void onActivityCreated() {
        assertNotNull(mainFragment.networkApi);
    }
}