package com.lipy.android.http;

import com.lipy.android.common.Constants;
import com.lipy.android.login.User;
import com.lipy.android.panel.JsonData;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    public void getData(final String requestName, final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        api.getData(requestName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<JsonData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<JsonData> jsonDataBaseResponse) {
                        onLoadDataFinishedListener.onSuccess(jsonDataBaseResponse.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onLoadDataFinishedListener.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void login(final String name, final String pwd, final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        api.login(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseResponse<User> jsonDataBaseResponse) {
                        User data = jsonDataBaseResponse.getData();
                        if (name.equals(data.getName()) && pwd.equals(data.getPwd())) {
                            onLoadDataFinishedListener.onSuccess(data);
                        } else {
                            onLoadDataFinishedListener.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onLoadDataFinishedListener.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
