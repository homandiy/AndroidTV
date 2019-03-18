package com.huang.homan.androidtv.Model;

import androidx.leanback.widget.HeaderItem;

/**
 * Model: supply data for each header item :
 *     title + icon     or      title only
 */
public class HeaderItemModel extends HeaderItem {

    private static final String TAG =
            HeaderItemModel.class.getSimpleName();
    public static final int ICON_NONE = -1;

    /**
     * Hold an icon resource id
     */
    private int mIconResId = ICON_NONE;

    // Title + Icon
    public HeaderItemModel(long id, String name, int iconResId) {
        super(id, name);
        mIconResId = iconResId;
    }

    // No icon, label only
    public HeaderItemModel(long id, String name) {
        this(id, name, ICON_NONE);
    }

    // Title label
    public HeaderItemModel(String name) {
        super(name);
    }

    public int getIconResId() {
        return mIconResId;
    }

    public void setIconResId(int iconResId) {
        this.mIconResId = iconResId;
    }
}
