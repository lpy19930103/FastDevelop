package com.lipy.android.net.callback;

import android.content.Context;

import com.lipy.android.net.dto.ServerModel;


/**
 * Created by lipy on 17/7/12.
 */

public interface OnDefaultResponseListener {

    void startLoading();

    void stopLoading();

    void success(ServerModel t);

    void error(String msg);

    Context getContext();
}
