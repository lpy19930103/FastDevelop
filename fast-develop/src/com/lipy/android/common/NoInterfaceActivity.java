package com.lipy.android.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lipy.android.common.nointerface.NoInterfaceListener;
import com.lipy.fastdevelop.R;

import java.lang.reflect.Constructor;

/**
 * 无界面回调
 * Created by lipy on 2017/5/5.
 */

public class NoInterfaceActivity extends Activity implements View.OnClickListener {
    public static final String LISTENER_KEY = "NoInterfaceActivity";
    private NoInterfaceListener mNoInterfaceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_tabcontainer);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);

        addListener();
    }

    private void addListener() {
        try {
            Class c = Class.forName(getIntent().getStringExtra(LISTENER_KEY));
            Constructor constructor = c.getConstructor(Activity.class);
            mNoInterfaceListener = (NoInterfaceListener) constructor.newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                if (mNoInterfaceListener != null) {
                    mNoInterfaceListener.onOneClick(this);
                }
                break;
            case R.id.btn_two:
                if (mNoInterfaceListener != null) {
                    mNoInterfaceListener.onTwoClick(this);
                }
                break;
            case R.id.btn_three:
                if (mNoInterfaceListener != null) {
                    mNoInterfaceListener.onThreeClick(this);
                }
                break;
            case R.id.btn_four:
                if (mNoInterfaceListener != null) {
                    mNoInterfaceListener.onFourClick(this);
                }
                break;
        }

    }
}
