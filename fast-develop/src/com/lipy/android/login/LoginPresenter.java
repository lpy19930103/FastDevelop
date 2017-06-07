package com.lipy.android.login;

/**
 * Created by lipy on 2017/6/7.
 */

interface LoginPresenter {

    void validateUser(String userName, String pwd);

    void onDestroy();

}
