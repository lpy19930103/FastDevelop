package com.lipy.android.http.api;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.lipy.android.common.Constants;
import com.lipy.android.http.Action;
import com.lipy.android.http.exception.Throwable;
import com.lipy.android.http.listener.OnLoadDataFinishedListener;
import com.lipy.android.http.response.BaseResponse;
import com.lipy.android.login.User;
import com.lipy.android.panel.JsonData;

import java.lang.reflect.Type;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lipy on 2017/6/8.
 */

public class ApiService {

    public static ApiService apiService;
    private final Api api;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public static ApiService getInstance() {
        if (apiService == null) {
            apiService = new ApiService();
        }
        return apiService;
    }

    public void getData(Context context, final String requestName, final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        new Action.Builder(context).baseUrl(Constants.URL).build().executeGet(requestName, stringObjectHashMap, new Action.ResponseCallBack<BaseResponse>() {
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
        new Action.Builder(context).baseUrl(Constants.URL).build().executeGet("user", stringObjectHashMap, new Action.ResponseCallBack<BaseResponse>() {
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
