package com.lipy.android.panel;

import android.util.Log;

import com.google.gson.Gson;
import com.lipy.android.common.Constants;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lipy on 2017/6/7.
 */

public class PanelModelImpl implements PanelModel {
    @Override
    public void loadData(final OnLoadDataFinishedListener onLoadDataFinishedListener) {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                //这里是登陆逻辑¬
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).build();
                Request test = retrofit.create(Request.class);
                Call<ResponseBody> blog = test.getBlog();

                blog.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            e.onNext(response.body().string());
                            Log.e("aaa", response.body().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        e.onError(t);
                    }
                });

            }
        })
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.e("accept", s);
                        Gson gson = new Gson();
                        JsonData fromJson = gson.fromJson(s, JsonData.class);
                        Log.e("aaa", fromJson.toString());
                        onLoadDataFinishedListener.onSuccess(fromJson);
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
