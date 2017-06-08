package com.lipy.android.panel;

import com.lipy.android.http.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/8.
 */

public interface PanelModel {
    void loadData(OnLoadDataFinishedListener onLoginFinishedListener);
}
