package com.lipy.android.net.callback;

import android.content.Context;


import com.lipy.android.net.dto.ServerModel;

import java.lang.reflect.Type;

/**
 * Created by lipy on 17/7/12.
 */

public interface OnResponseListener {
    void success(ServerModel t);

    void error(String msg);

    Context getContext();

    Object getTag();

    Type getType();

}
