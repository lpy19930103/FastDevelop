package com.lipy.android.login;

import com.lipy.android.http.DataObject;
import com.lipy.android.http.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/7.
 */

public class LoginPresenterImpl implements LoginPresenter, OnLoadDataFinishedListener {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView view) {
        loginView = view;
        loginModel = new loginModelImpl();
    }

    @Override
    public void validateUser(String userName, String pwd) {
        if (loginView != null) {
            loginView.showLoading();
        }
        loginModel.login(userName, pwd, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onError() {
        if (loginView != null) {
            loginView.hideLoading();
            loginView.showError();
        }
    }

    @Override
    public void onSuccess(DataObject data) {
        if (loginView != null) {
            loginView.hideLoading();
            loginView.loginSuccess();
        }
    }
}
