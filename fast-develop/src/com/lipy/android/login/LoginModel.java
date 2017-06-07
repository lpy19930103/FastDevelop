package com.lipy.android.login;

/**
 * Created by lipy on 2017/6/7.
 */

interface LoginModel {
    void login(String userName, String pwd, OnLoginFinishedListener onLoginFinishedListener);
}
