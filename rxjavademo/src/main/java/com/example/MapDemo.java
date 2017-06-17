package com.example;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by lipy on 2017/6/15.
 */

public class MapDemo {

    private static User[] users;

    public static void main(String[] args) {
        init();
        map();
    }

    public static void map() {
        Observable.fromArray(users)
                .map(new Function<User, String>() {
                    @Override
                    public String apply(@NonNull User user) throws Exception {
                        return user.getName();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    public static void flatMap() {
        Observable.fromArray(users)
//                .delay(10, TimeUnit.MILLISECONDS)
                .flatMap(new Function<User, Observable<String>>() {
                    @Override
                    public Observable<String> apply(@NonNull User user) throws Exception {
                        System.out.println("姓名：" + user.getName());
                        return Observable.fromArray(user.getSkills());
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String strings) throws Exception {
                        System.out.println("技能：" + strings);
                    }
                });
    }



    private static void init() {
        String[] skill1 = {"1-c++", "1-java","1-php"};
        String[] skill2 = {"2-java", "2-php","2-c"};
        String[] skill3 = {"3-python", "3-java","3-php"};
        String[] skill4 = {"4-c", "4-swift","4-objective-c"};
        User zhangsan = new User("1-zhangsan", skill1);
        User lisi = new User("2-lisi", skill2);
        User wangwu = new User("3-wangwu", skill3);
        User zhaoliu = new User("4-zhaoliu", skill4);
        users = new User[]{zhangsan, lisi, wangwu, zhaoliu};
    }
}

