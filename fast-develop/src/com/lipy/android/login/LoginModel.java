package com.lipy.android.login;

import com.lipy.android.http.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

interface LoginModel {
    void login(String userName, String pwd, OnLoadDataFinishedListener onLoginFinishedListener);
}
