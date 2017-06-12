package com.lipy.android.panel;

import android.content.Context;

import com.lipy.android.http.listener.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/8.
 */

public interface PanelModel {
    void loadData(Context context,OnLoadDataFinishedListener onLoginFinishedListener);
}
