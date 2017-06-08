package com.lipy.android.http;

/**
 * Created by lipy on 2017/6/7.
 */

public interface OnLoadDataFinishedListener {

    void onError();

    void onSuccess(DataObject data);
}
