package com.huang.homan.androidtv.Dagger.Module;

import android.app.Activity;

import com.huang.homan.androidtv.Dagger.Scope.AdapterGridRow;
import com.huang.homan.androidtv.Dagger.Scope.AdapterListRow;
import com.huang.homan.androidtv.Dagger.Scope.AdapterRow;
import com.huang.homan.androidtv.Presenter.CardPresenter;
import com.huang.homan.androidtv.View.Fragment.MainFragment;
import com.huang.homan.androidtv.View.Fragment.MainFragment.GridItemPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRowPresenter;
import dagger.Module;
import dagger.Provides;


@Module
public class LoadRowsModule {

    /**
     * ArrayObjectAdapter rowsAdapter =
     *          new ArrayObjectAdapter(
     *                  new ListRowPresenter());
     */
    @Provides
    public ListRowPresenter provideListRowPresenter() {
        return new ListRowPresenter();
    }

    @Provides
    @AdapterRow
    public ArrayObjectAdapter provideRowsAdapter(ListRowPresenter listRowPresenter) {
        return new ArrayObjectAdapter(listRowPresenter);
    }

    // CardPresenter cardPresenter = new CardPresenter();
    @Provides
    public CardPresenter provideCardPresenter() {
        return new CardPresenter();
    }

    // ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
    @Provides
    @AdapterListRow
    public ArrayObjectAdapter provideListRowAdapter(
            CardPresenter cardPresenter) {
        return new ArrayObjectAdapter(cardPresenter);
    }

    //        GridItemPresenter mGridPresenter =
    //                new GridItemPresenter();
    //        ArrayObjectAdapter gridRowAdapter =
    //                new ArrayObjectAdapter(mGridPresenter);
    @Provides
    public GridItemPresenter provideGridItemPresenter(Activity activity) {
        return GridItemPresenter.newInstance(activity);
    }

    @Provides
    @AdapterGridRow
    public ArrayObjectAdapter provideGridRowAdapter(
            GridItemPresenter gridItemPresenter) {
        return new ArrayObjectAdapter(gridItemPresenter);
    }

}
