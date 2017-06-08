package com.lipy.android.panel;

import android.util.Log;

import com.lipy.android.http.DataObject;
import com.lipy.android.http.OnLoadDataFinishedListener;

/**
 * Created by lipy on 2017/6/8.
 */

public class PanelPresenterImpl implements PanelPresenter, OnLoadDataFinishedListener {
    private PanelView mPanelView;
    private PanelModel panelModel;

    public PanelPresenterImpl(PanelView panelView) {
        mPanelView = panelView;
        panelModel = new PanelModelImpl();
    }

    @Override
    public void loadData() {
        if (mPanelView != null) {
            mPanelView.showLoading();
        }
        panelModel.loadData(this);
    }

    @Override
    public void onDestroy() {
        mPanelView = null;
    }

    @Override
    public void onError() {
        if (mPanelView != null) {
            mPanelView.hideLoading();
            mPanelView.showError();
        }
    }

    @Override
    public void onSuccess(DataObject data) {
        Log.e("P", "onSuccess");
        if (mPanelView != null) {
            mPanelView.hideLoading();
            mPanelView.showSuccess((JsonData) data);
        }
    }
}
