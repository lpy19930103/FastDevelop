package com.lipy.android.http.listener;

import com.lipy.android.http.response.DataObject;

/**
 * Created by lipy on 2017/6/7.
 */

public interface OnLoadDataFinishedListener {

    void onError();

    void onSuccess(DataObject data);
}
