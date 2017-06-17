package com.example;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;

/**
 * Created by lipy on 2017/6/14.
 */

public class Demo2 {

    public static void main(String[] args){
        Flowable.range(0, 10000)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        s.request(10000);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
