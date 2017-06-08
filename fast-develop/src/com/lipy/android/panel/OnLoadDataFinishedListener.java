package com.lipy.android.panel;

/**
 * Created by lipy on 2017/6/7.
 */

interface OnLoadDataFinishedListener {

    void onError();

    void onSuccess(JsonData data);
}
