package com.lipy.android.panel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lipy on 2017/6/7.
 */

public interface Request {
    @GET("city.json")
    Call<ResponseBody> getBlog();
}
