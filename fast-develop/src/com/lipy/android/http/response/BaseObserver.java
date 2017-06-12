package com.lipy.android.http.response;

import android.util.Log;

import com.lipy.android.http.exception.ActionException;
import com.lipy.android.http.exception.Throwable;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by lipy on 2017/6/9.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }


    @Override
    public void onError(@NonNull java.lang.Throwable e) {
        Log.v("Action", e.getMessage());
        if(e instanceof Throwable){
            Log.e("Action", "--> e instanceof Throwable"+ e);
            onError((Throwable)e);
        } else {
            Log.e("Action", "e !instanceof Throwable" + e);
            onError(new Throwable(e, ActionException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(Throwable e);
}
