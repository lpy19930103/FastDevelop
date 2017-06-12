package com.lipy.android.panel;

import android.content.Context;

import com.lipy.android.http.api.ApiService;
import com.lipy.android.http.listener.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

public class PanelModelImpl implements PanelModel {
    @Override
    public void loadData(Context context, final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        ApiService.getInstance().getData(context, "city3", onLoadDataFinishedListener);
    }
}
