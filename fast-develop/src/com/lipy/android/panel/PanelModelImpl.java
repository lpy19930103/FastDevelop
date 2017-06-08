package com.lipy.android.panel;

import com.lipy.android.http.ApiService;
import com.lipy.android.http.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

public class PanelModelImpl implements PanelModel {
    @Override
    public void loadData(final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        ApiService.getInstance().getData("city3", onLoadDataFinishedListener);
    }
}
