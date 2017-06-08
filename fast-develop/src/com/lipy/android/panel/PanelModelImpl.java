package com.lipy.android.panel;

import com.lipy.android.common.Constants;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lipy on 2017/6/7.
 */

public class PanelModelImpl implements PanelModel {
    @Override
    public void loadData(final OnLoadDataFinishedListener onLoadDataFinishedListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Request test = retrofit.create(Request.class);
        test.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull JsonData jsonData) {
                        onLoadDataFinishedListener.onSuccess(jsonData);
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
