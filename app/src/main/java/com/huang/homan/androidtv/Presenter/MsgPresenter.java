package com.huang.homan.androidtv.Presenter;

import com.huang.homan.androidtv.Helper.BaseActivityVP;
import com.huang.homan.androidtv.View.Activity.MainActivity;

public class MsgPresenter implements BaseActivityVP.Presenter {

    private MainActivity activity;

    public MsgPresenter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void sendMessage(String msg) {
        activity.showMessage(msg);
    }
}
