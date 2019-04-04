/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.huang.homan.androidtv.View.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import dagger.android.AndroidInjection;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.huang.homan.androidtv.Dagger.Scope.ActivityDependency;
import com.huang.homan.androidtv.Dagger.Scope.AdapterGridRow;
import com.huang.homan.androidtv.Dagger.Scope.AdapterListRow;
import com.huang.homan.androidtv.Dagger.Scope.AdapterRow;
import com.huang.homan.androidtv.Dagger.Scope.AppDependency;
import com.huang.homan.androidtv.Dagger.Scope.FragmentDependency;
import com.huang.homan.androidtv.Data.Movie;
import com.huang.homan.androidtv.Data.MovieList;
import com.huang.homan.androidtv.Data.MyHeaderList;
import com.huang.homan.androidtv.Model.HeaderItemModel;
import com.huang.homan.androidtv.Model.NetworkApi;
import com.huang.homan.androidtv.Presenter.MsgPresenter;
import com.huang.homan.androidtv.Presenter.MyHeaderItemPresenter;
import com.huang.homan.androidtv.R;
import com.huang.homan.androidtv.View.Activity.BrowseErrorActivity;
import com.huang.homan.androidtv.View.Activity.DetailsActivity;
import com.huang.homan.androidtv.View.Activity.MainActivity;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Named;

public class MainFragment extends BrowseFragment {
    /* Log tag and shortcut */
    final static String TAG = "MYLOG "+ MainFragment.class.getSimpleName();
    public static void ltag(String message) { Log.i(TAG, message); }

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    //private static final int NUM_ROWS = 6;

    private static final int NUM_COLS = 15;
    public int getNumCols() { return NUM_COLS; }

    private final Handler mHandler = new Handler();

    private Timer mBackgroundTimer;
    private String mBackgroundUri;

    MsgPresenter presenter;

    @Override
    public void onAttach(Context context) {
        ltag("onAttach");

        AndroidInjection.inject(this);

        super.onAttach(context);
    }

    @Inject
    NetworkApi networkApi;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        presenter = ((MainActivity)getActivity()).getPresenter();

        // Test DI on fragment
        boolean injected = networkApi != null;
        ltag(
                TAG+":\nDependency injection\nworked? " +
                        String.valueOf(injected));


        setupUIElements();
        loadRows();
        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    @Inject
    @AdapterRow
    ArrayObjectAdapter rowsAdapter;

    @Inject
    @AdapterListRow
    ArrayObjectAdapter listRowAdapter;

    @Inject
    @AdapterGridRow
    ArrayObjectAdapter gridRowAdapter;

    private void loadRows() {
        List<Movie> list = MovieList.setupMovies();

        int i;
        String[] myHeader = MyHeaderList.HEADER_CATEGORY;

        for (i = 0; i < myHeader.length; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }

            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list.get(j % 5));
            }
            //add category with icon
            HeaderItemModel header = new HeaderItemModel(
                    i,
                    MyHeaderList.HEADER_CATEGORY[i],
                    R.drawable.header_icon);
            rowsAdapter.add(new ListRow(header, listRowAdapter));

        }

        HeaderItemModel gridHeader =
                new HeaderItemModel(i, "PREFERENCES");

        gridRowAdapter.add(getResources().getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
        rowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(rowsAdapter);
    }

    // Get new fragment
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Inject
    AppDependency appDependency; // same object from App

    @Inject
    ActivityDependency activityDependency; // same object from MainActivity

    @Inject
    FragmentDependency fragmentDependency;

    @Inject
    BackgroundManager mBackgroundManager;

    @Inject
    DisplayMetrics mMetrics;

    @Inject
    Drawable mDefaultBackground;

    private void setupUIElements() {
        setBadgeDrawable(getActivity().getResources().getDrawable(
                R.drawable.company_logo));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.my_header_background));
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));

        // customize header
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return new MyHeaderItemPresenter();
            }
        });
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),
                        "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<Drawable>(width, height) {
                    @Override
                    public void onResourceReady(
                            @NonNull Drawable resource,
                            @Nullable Transition<? super Drawable> transition) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.error_fragment))) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(
                Presenter.ViewHolder itemViewHolder,
                Object item,
                RowPresenter.ViewHolder rowViewHolder,
                Row row) {

            if (item instanceof Movie) {
                mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
                startBackgroundTimer();
            }
        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateBackground(mBackgroundUri);
                }
            });
        }
    }

    public static class GridItemPresenter extends Presenter {

        Activity activity;

        public static GridItemPresenter newInstance(Activity activity) {
            GridItemPresenter gridItemPresenter = new GridItemPresenter();
            gridItemPresenter.activity = activity;
            return gridItemPresenter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(
                    ContextCompat.getColor(activity, R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
