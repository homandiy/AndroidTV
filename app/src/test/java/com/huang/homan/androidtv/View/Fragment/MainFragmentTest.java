package com.huang.homan.androidtv.View.Fragment;

import android.app.Activity;

import com.huang.homan.androidtv.BuildConfig;
import com.huang.homan.androidtv.R;
import com.huang.homan.androidtv.View.Activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.fragment.app.FragmentTransaction;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class MainFragmentTest {

    private MainActivity mainActivity;
    private MainFragment mainFragment;

    @Before
    public void setUp() throws Exception
    {
        mainActivity = Robolectric.buildActivity( MainActivity.class )
                .create()
                .resume()
                .get();
        mainFragment = MainFragment.newInstance();
        startFragment(mainFragment);

    }

    private void startFragment(MainFragment fragment) {
        mainActivity.getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.mainCL, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( mainActivity );
        assertNotNull( mainFragment );
    }

    @Test
    public void onActivityCreated() {
        assertNotNull( mainFragment.networkApi );

        // background manager test
        assertNotNull( mainFragment.mBackgroundManager );
        assertNotNull( mainFragment.mDefaultBackground );
        assertNotNull( mainFragment.mMetrics );

        // loadRows test
        assertNotNull( mainFragment.rowsAdapter );
        assertNotNull( mainFragment.listRowAdapter );
        assertNotNull( mainFragment.gridRowAdapter );

    }
}

