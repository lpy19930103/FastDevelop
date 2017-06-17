package com.example;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

/**
 * Created by lipy on 2017/6/14.
 */

public class Demo1 {
    public static void main(String[] args){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(0);
                System.out.println("ObservableEmitter = " + 0);
                e.onNext(1);
                System.out.println("ObservableEmitter = " + 1);
                e.onNext(2);
                System.out.println("ObservableEmitter = " + 2);
                e.onComplete();
                e.onNext(3);

            }
        });


        //偷懒模式1
        Observable<Integer> observable2 = Observable.just(10, 11, 12, 13);
        //偷懒模式2
        Integer[] integers = {4, 5, 6, 7};
        Observable observable3 = Observable.fromArray(integers);

        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
//                d.dispose();
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer o) throws Exception {
                System.out.println("1---" + o);
            }
        };
        Consumer<Integer> consumer1 = new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("2---" + integer);
            }
        };
        ;
        observable1.subscribe(consumer);
        System.out.println("subscribe");
        observable1.subscribe(consumer1);


        new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer integer, Integer integer2) throws Exception {

            }
        };

    }

}
