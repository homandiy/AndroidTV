package com.huang.homan.androidtv.Presenter;

import android.graphics.drawable.Drawable;

import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.Presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huang.homan.androidtv.Model.HeaderItemModel;
import com.huang.homan.androidtv.R;


public class HeaderPresenter extends Presenter {

    private static final String TAG = HeaderPresenter.class.getSimpleName();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.header_item, null);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        HeaderItemModel headerItem = (HeaderItemModel) ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;

        ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
        Drawable icon = rootView.getResources().getDrawable(R.drawable.header_icon, null);
        iconView.setImageDrawable(icon);

        TextView label = (TextView) rootView.findViewById(R.id.header_label);
        label.setText(headerItem.getName());

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }


}
