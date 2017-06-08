package com.lipy.android.panel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by lipy on 2017/6/7.
 */

public interface Request {
    @GET("city.json")
    Observable<JsonData> getData();
}
