package com.lipy.android.http;

import com.lipy.android.login.User;
import com.lipy.android.panel.JsonData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lipy on 2017/6/8.
 */

public interface Api {
    @GET("{city3}.json")
    Observable<BaseResponse<JsonData>> getData(@Path("city3") String className);

    @POST("user.json")
    Observable<BaseResponse<User>> login(@Body() User user);
}
