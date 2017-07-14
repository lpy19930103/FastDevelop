package com.lipy.android.net;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lipy.android.http.response.DataObject;
import com.lipy.android.net.callback.OnDefaultResponeListener;
import com.lipy.android.net.callback.OnResponeListener;
import com.lipy.android.net.dto.ServerModel;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;

/**
 * Created by mc on 17/7/12.
 */

public class Action<T extends DataObject> {
    private ActionService<T> mActionService;
    private OnDefaultResponeListener mView;

    public Action(OnDefaultResponeListener view) {
        mView = view;
        mActionService = new ActionService<>();
    }

    public void getUser(final String tag, HttpParams httpParams) {
        mView.startLoading();
        mActionService.request(httpParams, new OnResponeListener() {

            @Override
            public void success(ServerModel tServerModel) {
                mView.stopLoading();
                mView.success(tServerModel);
            }

            @Override
            public void error(String msg) {
                mView.stopLoading();
                mView.error(msg);
            }

            @Override
            public Context getContext() {
                return mView.getContext();
            }

            @Override
            public Object getTag() {
                return tag;
            }

            @Override
            public Type getType() {
                return new TypeToken<ServerModel<User>>() {
                }.getType();
            }
        });
    }

    public void cancel(String tag){
        mActionService.cancle(tag);
    }
}
