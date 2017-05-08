package com.lipy.android.common.nointerface;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by lipy on 2017/5/5.
 */

public class NoInterfaceListenerImp implements NoInterfaceListener {

    public NoInterfaceListenerImp(Activity activity) {
    }

    @Override
    public void onOneClick(Context context) {
        Toast.makeText(context, "onOneClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTwoClick(Context context) {
        Toast.makeText(context, "onTwoClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onThreeClick(Context context) {
        Toast.makeText(context, "onThreeClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFourClick(Context context) {
        Toast.makeText(context, "onFourClick", Toast.LENGTH_SHORT).show();
    }
}
