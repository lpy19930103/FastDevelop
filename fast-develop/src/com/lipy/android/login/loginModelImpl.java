package com.lipy.android.login;

import com.lipy.android.http.ApiService;
import com.lipy.android.http.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

public class loginModelImpl implements LoginModel {
    @Override
    public void login(String userName, String pwd, final OnLoadDataFinishedListener onLoginFinishedListener) {

        ApiService.getInstance().login(userName,pwd,onLoginFinishedListener);

    }

}