package com.lipy.android.login;

/**
 * Created by lipy on 2017/6/7.
 */

public class loginModelImpl implements LoginModel {
    @Override
    public void login(String userName, String pwd, final OnLoginFinishedListener onLoginFinishedListener) {


//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
//                //这里是登陆逻辑¬
//                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).build();
//                Request test = retrofit.create(Request.class);
//                test.getBlog();
//
//                blog.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        try {
////                            Log.e("aaa", response.body().string());
//                            e.onNext(response.body().string());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                    }
//                });
//
//            }
//        }).delay(2000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.e("accept", s);
//                        Gson gson = new Gson();
//                        JsonData fromJson = gson.fromJson(s, JsonData.class);
//                        Log.e("aaa", fromJson.toString());
//                        onLoginFinishedListener.onSuccess();
//                    }
//                });
    }

}