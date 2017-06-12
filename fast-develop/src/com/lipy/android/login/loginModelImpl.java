package com.lipy.android.login;

import android.content.Context;

import com.lipy.android.http.ApiService;
import com.lipy.android.http.listener.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

public class loginModelImpl implements LoginModel {
    @Override
    public void login(Context context, String userName, String pwd, final OnLoadDataFinishedListener onLoginFinishedListener) {

        ApiService.getInstance().login(context, userName, pwd, onLoginFinishedListener);

    }

}