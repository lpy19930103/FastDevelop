package com.lipy.android.login;

/**
 * Created by lipy on 2017/6/7.
 */

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {
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
    public void onSuccess() {
        if (loginView != null) {
            loginView.hideLoading();
            loginView.loginSuccess();
        }
    }
}
