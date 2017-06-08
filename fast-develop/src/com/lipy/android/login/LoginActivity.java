package com.lipy.android.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lipy.fastdevelop.R;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    private EditText mNameET;
    private EditText mPwdET;
    private LoginPresenterImpl mLoginPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenterImpl(this);

        mNameET = (EditText) findViewById(R.id.login_edit_name);
        mPwdET = (EditText) findViewById(R.id.login_edit_pwd);
        findViewById(R.id.login).setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("正在加载...");
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        Log.e("hhh","aaa");
        mProgressDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mLoginPresenter.validateUser(mNameET.getText().toString().trim(), mPwdET.getText().toString().trim());
                break;
        }
    }
}
