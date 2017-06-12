package com.lipy.android.login;

import android.content.Context;

import com.lipy.android.http.listener.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

interface LoginModel {
    void login(Context context, String userName, String pwd, OnLoadDataFinishedListener onLoginFinishedListener);
}
