package com.lipy.android.panel;

import android.content.Context;

import com.lipy.android.common.BaseView;

/**
 * Created by lipy on 2017/6/8.
 */

public interface PanelView extends BaseView {

    void showSuccess(JsonData bigData);

    void showError();

    Context getContext();


}
