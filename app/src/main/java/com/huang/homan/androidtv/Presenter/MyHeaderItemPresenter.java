package com.huang.homan.androidtv.Presenter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.homan.androidtv.Model.HeaderItemModel;
import com.huang.homan.androidtv.R;

import java.util.List;

import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowHeaderPresenter;

public class MyHeaderItemPresenter extends RowHeaderPresenter {

    private static final String TAG = MyHeaderItemPresenter.class.getSimpleName();
    private static void ltag(String message) { Log.i(TAG, message); }

    private float mUnselectedAlpha;
    private int lastRow;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mUnselectedAlpha = parent.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.header_item, null);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        HeaderItemModel iconHeaderItem = (HeaderItemModel) ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;

        ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);

        int iconResId = iconHeaderItem.getIconResId();
        if( iconResId != HeaderItemModel.ICON_NONE) { // Show icon only when it is set.
            Drawable icon = rootView.getResources().getDrawable(iconResId, null);
            iconView.setImageDrawable(icon);
        }

        TextView label = (TextView) rootView.findViewById(R.id.header_label);
        label.setText(iconHeaderItem.getName());
    }

    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        // this is a temporary fix
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
    }
}
