package com.lipy.android.login;

import android.content.Context;

import com.lipy.android.common.BaseView;

/**
 * Created by lipy on 2017/6/7.
 */

public interface LoginView extends BaseView {

    void showError();

    void loginSuccess();

    Context getContext();


}
