package com.lipy.android.http.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by lipy on 2017/6/8.
 */

public interface Api {

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> executePost(
            @Url() String url,
            @FieldMap Map<String, Object> maps);


    @GET("{path}.json")
    Observable<ResponseBody> executeGet(
            @Path("path") String requestName,
            @QueryMap Map<String, Object> maps);

}
