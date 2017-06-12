package com.lipy.android.http;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lipy.android.common.Constants;
import com.lipy.android.http.api.ApiAction;
import com.lipy.android.http.exception.Throwable;
import com.lipy.android.http.listener.OnLoadDataFinishedListener;
import com.lipy.android.http.response.BaseResponse;
import com.lipy.android.login.User;
import com.lipy.android.panel.JsonData;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by lipy on 2017/6/8.
 */

public class ApiService {

    public static ApiService apiService;

    public static ApiService getInstance() {
        if (apiService == null) {
            apiService = new ApiService();
        }
        return apiService;
    }

    public void getData(Context context, final String requestName, final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        new ApiAction.Builder(context).baseUrl(Constants.URL).build().executeGet(requestName, stringObjectHashMap, new ApiAction.ResponseCallBack<BaseResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                onLoadDataFinishedListener.onError();
            }

            @Override
            public void onSuccee(BaseResponse response) {
                onLoadDataFinishedListener.onSuccess(response.getData());
            }

            @Override
            public Type getType() {
                return new TypeToken<BaseResponse<JsonData>>() {
                }.getType();
            }
        });

    }


    public void login(Context context, final String name, final String pwd, final OnLoadDataFinishedListener onLoadDataFinishedListener) {

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        new ApiAction.Builder(context).baseUrl(Constants.URL).build().executeGet("user", stringObjectHashMap, new ApiAction.ResponseCallBack<BaseResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                onLoadDataFinishedListener.onError();
            }

            @Override
            public void onSuccee(BaseResponse response) {
                User data = (User) response.getData();
                if (name.equals(data.getName()) && pwd.equals(data.getPwd())) {
                    onLoadDataFinishedListener.onSuccess(data);
                } else {
                    onLoadDataFinishedListener.onError();
                }
            }

            @Override
            public Type getType() {
                return new TypeToken<BaseResponse<User>>() {
                }.getType();
            }
        });

    }
}
