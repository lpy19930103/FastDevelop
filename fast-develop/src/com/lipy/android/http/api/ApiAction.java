package com.lipy.android.http.api;

import android.content.Context;
import android.nfc.FormatException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.lipy.android.http.cookie.ActionCookieManager;
import com.lipy.android.http.cookie.CookieCacheImpl;
import com.lipy.android.http.cookie.SharedPrefsCookiePersistor;
import com.lipy.android.http.exception.ActionException;
import com.lipy.android.http.exception.ServerException;
import com.lipy.android.http.exception.Throwable;
import com.lipy.android.http.https.ActionHttpsFactroy;
import com.lipy.android.http.interceptor.BaseInterceptor;
import com.lipy.android.http.interceptor.CacheInterceptor;
import com.lipy.android.http.interceptor.CacheInterceptorOffline;
import com.lipy.android.http.request.SchedulersTransFormer;
import com.lipy.android.http.response.BaseObserver;
import com.lipy.android.http.response.BaseResponse;
import com.lipy.android.http.utils.Utils;

import java.io.File;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;

/**
 * 基于 Novate 改造
 * Novate adapts a Java interface to Retrofit call by using annotations on the declared methods to
 * define how requests are made. Create instances using {@linkplain Builder
 * the builder} and pass your interface to {@link #} to generate an implementation.
 * <p/>
 * For example,
 * <pre>{@code
 * Novate novate = new Novate.Builder()
 *     .baseUrl("http://api.example.com")
 *     .addConverterFactory(GsonConverterFactory.create())
 *     .build();
 * <p/>
 * MyApi api = Novate.create(MyApi.class);
 * Response<User> user = api.getUser().execute();
 * }</pre>
 */
public final class ApiAction {
    private static Map<String, String> headers;
    private static Map<String, String> parameters;
    private static Retrofit.Builder retrofitBuilder;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder okhttpBuilder;
    public static Api apiManager;
    private static OkHttpClient okHttpClient;
    private static Context mContext;
    private final okhttp3.Call.Factory callFactory;
    private final String baseUrl;

    public static final String TAG = "ApiAction";

    /**
     * Mandatory constructor for the ApiAction
     */
    ApiAction(okhttp3.Call.Factory callFactory, String baseUrl, Map<String, String> headers,
              Map<String, String> parameters, Api apiManager
    ) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.headers = headers;
        this.parameters = parameters;
        this.apiManager = apiManager;
    }

    /**
     * create ApiService
     */
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * Retroift get
     */
    public void get(String url, Map<String, Object> maps, BaseObserver<ResponseBody> subscriber) {
        apiManager.executeGet(url, maps)
                .compose(new SchedulersTransFormer<ResponseBody>())
                .subscribe(subscriber);
    }

    /**
     * ApiAction execute get
     */
    public void executeGet(final String url, final Map<String, Object> maps, final ResponseCallBack<BaseResponse> callBack) {
        apiManager.executeGet(url, maps)
                .compose(new SchedulersTransFormer<ResponseBody>())
                .subscribe(new ActionObserverr<BaseResponse>(callBack));

    }


    /**
     * ApiAction executePost
     */
    public void post(String url, @FieldMap(encoded = true) Map<String, Object> parameters, BaseObserver<ResponseBody> subscriber) {
        apiManager.executePost(url, parameters)
                .compose(new SchedulersTransFormer<ResponseBody>())
                .subscribe(subscriber);
    }


    /**
     * ApiAction executePost
     */
    public void executePost(final String url, @FieldMap(encoded = true) Map<String, Object> parameters, final ResponseCallBack<ResponseBody> callBack) {

        apiManager.executePost(url, parameters)
                .compose(new SchedulersTransFormer<ResponseBody>())
                .subscribe(new ActionObserverr<ResponseBody>(callBack));
    }

    /**
     * Mandatory Builder for the Builder
     */
    public static final class Builder {

        private static final int DEFAULT_TIMEOUT = 5;
        private static final int DEFAULT_MAXIDLE_CONNECTIONS = 5;
        private static final long DEFAULT_KEEP_ALIVEDURATION = 8;
        private static final long caheMaxSize = 10 * 1024 * 1024;

        private okhttp3.Call.Factory callFactory;
        private String baseUrl;
        private Boolean isLog = false;
        private Boolean isCookie = false;
        private Boolean isCache = true;
        private HostnameVerifier hostnameVerifier;
        private CertificatePinner certificatePinner;
        private Context context;
        private ActionCookieManager cookieManager;
        private Cache cache = null;
        private Proxy proxy;
        private File httpCacheDirectory;
        private SSLSocketFactory sslSocketFactory;
        private ConnectionPool connectionPool;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory callAdapterFactory;
        private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR;
        private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE;

        public Builder(Context context) {
            okhttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
            this.context = context;

        }

        /**
         * The HTTP client used for requests. default OkHttpClient
         * <p/>
         * This is a convenience method for calling {@link #callFactory}.
         * <p/>
         * Note: This method <b>does not</b> make a defensive copy of {@code client}. Changes to its
         * settings will affect subsequent requests. Pass in a {@linkplain OkHttpClient#clone() cloned}
         * instance to prevent this if desired.
         */
        @NonNull
        public Builder client(OkHttpClient client) {
            retrofitBuilder.client(Utils.checkNotNull(client, "client == null"));
            return this;
        }

        /**
         * Add ApiManager for serialization and deserialization of objects.
         *//*
        public Builder addApiManager(final Class<ApiManager> service) {

            apiManager = retrofit.create((Utils.checkNotNull(service, "apiManager == null")));
            //return retrofit.create(service);
            return this;
        }*/

        /**
         * Specify a custom call factory for creating {@link } instances.
         * <p/>
         * Note: Calling {@link #client} automatically sets this value.
         */
        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(int timeout) {
            return connectTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder writeTimeout(int timeout) {
            return writeTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * open default logcat
         *
         * @param isLog
         * @return
         */
        public Builder addLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }

        /**
         * open sync default Cookie
         *
         * @param isCookie
         * @return
         */
        public Builder addCookie(boolean isCookie) {
            this.isCookie = isCookie;
            return this;
        }

        /**
         * open default Cache
         *
         * @param isCache
         * @return
         */
        public Builder addCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Builder proxy(Proxy proxy) {
            okhttpBuilder.proxy(Utils.checkNotNull(proxy, "proxy == null"));
            return this;
        }


        /**
         * Sets the default write timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link TimeUnit #MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                okhttpBuilder.writeTimeout(timeout, unit);
            } else {
                okhttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        /**
         * Sets the connection pool used to recycle HTTP and HTTPS connections.
         * <p>
         * <p>If unset, a new connection pool will be used.
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) throw new NullPointerException("connectionPool == null");
            this.connectionPool = connectionPool;
            return this;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link TimeUnit #MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                okhttpBuilder.connectTimeout(timeout, unit);
            } else {
                okhttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }


        /**
         * Set an API base URL which can change over time.
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = Utils.checkNotNull(baseUrl, "baseUrl == null");
            return this;
        }

        /**
         * Add converter factory for serialization and deserialization of objects.
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactory = factory;
            return this;
        }

        /**
         * Add a call adapter factory for supporting service method return types other than {@link CallAdapter
         * }.
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactory = factory;
            return this;
        }

        /**
         * Add Header for serialization and deserialization of objects.
         */
        public <T> Builder addHeader(Map<String, T> headers) {
            okhttpBuilder.addInterceptor(new BaseInterceptor(Utils.checkNotNull(headers, "header == null")));
            return this;
        }

        /**
         * Add parameters for serialization and deserialization of objects.
         */
        public <T> Builder addParameters(Map<String, T> parameters) {
            okhttpBuilder.addInterceptor(new BaseInterceptor(Utils.checkNotNull(parameters, "parameters == null")));
            return this;
        }

        /**
         * Returns a modifiable list of interceptors that observe a single network request and response.
         * These interceptors must call {@link Interceptor.Chain#proceed} exactly once: it is an error
         * for a network interceptor to short-circuit or repeat a network request.
         */
        public Builder addInterceptor(Interceptor interceptor) {
            okhttpBuilder.addInterceptor(Utils.checkNotNull(interceptor, "interceptor == null"));
            return this;
        }


        /**
         * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
         * outgoing HTTP requests.
         * <p/>
         * <p>If unset, {@linkplain ActionCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
         */
        public Builder cookieManager(ActionCookieManager cookie) {
            if (cookie == null) throw new NullPointerException("cookieManager == null");
            this.cookieManager = cookie;
            return this;
        }

        /**
         *
         */
        public Builder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public Builder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder addCertificatePinner(CertificatePinner certificatePinner) {
            this.certificatePinner = certificatePinner;
            return this;
        }


        /**
         * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
         * outgoing HTTP requests.
         * <p/>
         * <p>If unset, {@linkplain ActionCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
         */
        public Builder addSSL(String[] hosts, int[] certificates) {
            if (hosts == null) throw new NullPointerException("hosts == null");
            if (certificates == null) throw new NullPointerException("ids == null");


            addSSLSocketFactory(ActionHttpsFactroy.getSSLSocketFactory(context, certificates));
            addHostnameVerifier(ActionHttpsFactroy.getHostnameVerifier(hosts));
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            okhttpBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        /**
         * setCache
         *
         * @param cache cahe
         * @return Builder
         */
        public Builder addCache(Cache cache) {
            int maxStale = 60 * 60 * 24 * 3;
            return addCache(cache, maxStale);
        }

        /**
         * @param cache
         * @param cacheTime ms
         * @return
         */
        public Builder addCache(Cache cache, final int cacheTime) {
            addCache(cache, String.format("max-age=%d", cacheTime));
            return this;
        }

        /**
         * @param cache
         * @param cacheControlValue Cache-Control
         * @return
         */
        private Builder addCache(Cache cache, final String cacheControlValue) {
            REWRITE_CACHE_CONTROL_INTERCEPTOR = new CacheInterceptor(mContext, cacheControlValue);
            REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE = new CacheInterceptorOffline(mContext, cacheControlValue);
            addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
            addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
            this.cache = cache;
            return this;
        }

        /**
         * Create the {@link Retrofit} instance using the configured values.
         * <p/>
         * Note: If neither {@link #client} nor {@link #callFactory} is called a default {@link
         * OkHttpClient} will be created and used.
         */
        public ApiAction build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }

            if (okhttpBuilder == null) {
                throw new IllegalStateException("okhttpBuilder required.");
            }

            if (retrofitBuilder == null) {
                throw new IllegalStateException("retrofitBuilder required.");
            }
            /** set Context. */
            mContext = context;
            /**
             * Set a fixed API base URL.
             *
             * @see #baseUrl(HttpUrl)
             */
            retrofitBuilder.baseUrl(baseUrl);

            /** Add converter factory for serialization and deserialization of objects. */
            if (converterFactory == null) {
                converterFactory = GsonConverterFactory.create();
            }

            retrofitBuilder.addConverterFactory(converterFactory);
            /**
             * Add a call adapter factory for supporting service method return types other than {@link
             * Call}.
             */
            if (callAdapterFactory == null) {
                callAdapterFactory = RxJava2CallAdapterFactory.create();
            }
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory);

            if (isLog) {
                okhttpBuilder.addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
            }

            if (sslSocketFactory != null) {
                okhttpBuilder.sslSocketFactory(sslSocketFactory);
            }

            if (hostnameVerifier != null) {
                okhttpBuilder.hostnameVerifier(hostnameVerifier);
            }


            if (httpCacheDirectory == null) {
                httpCacheDirectory = new File(mContext.getCacheDir(), "Action_Http_cache");
            }

            if (isCache) {
                try {
                    if (cache == null) {
                        cache = new Cache(httpCacheDirectory, caheMaxSize);
                    }

                    addCache(cache);

                } catch (Exception e) {
                    Log.e("OKHttp", "Could not create http cache", e);
                }
                if (cache == null) {
                    cache = new Cache(httpCacheDirectory, caheMaxSize);
                }
            }

            if (cache != null) {
                okhttpBuilder.cache(cache);
            }

            /**
             * Sets the connection pool used to recycle HTTP and HTTPS connections.
             *
             * <p>If unset, a new connection pool will be used.
             */
            if (connectionPool == null) {

                connectionPool = new ConnectionPool(DEFAULT_MAXIDLE_CONNECTIONS, DEFAULT_KEEP_ALIVEDURATION, TimeUnit.SECONDS);
            }
            okhttpBuilder.connectionPool(connectionPool);

            /**
             * Sets the HTTP proxy that will be used by connections created by this client. This takes
             * precedence over {@link #proxySelector}, which is only honored when this proxy is null (which
             * it is by default). To disable proxy use completely, call {@code setProxy(Proxy.NO_PROXY)}.
             */
            if (proxy == null) {
                okhttpBuilder.proxy(proxy);
            }

            /**
             * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
             * outgoing HTTP requests.
             *
             * <p>If unset, {@link ApiAction ActionCookieManager#NO_COOKIES no cookies} will be accepted nor provided.
             */
            if (isCookie && cookieManager == null) {
                //okhttpBuilder.cookieJar(new ActionCookieManger(context));
                okhttpBuilder.cookieJar(new ActionCookieManager(new CookieCacheImpl(), new SharedPrefsCookiePersistor(context)));

            }

            if (cookieManager != null) {
                okhttpBuilder.cookieJar(cookieManager);
            }

            /**
             *okhttp3.Call.Factory callFactory = this.callFactory;
             */
            if (callFactory != null) {
                retrofitBuilder.callFactory(callFactory);
            }
            /**
             * create okHttpClient
             */
            okHttpClient = okhttpBuilder.build();
            /**
             * set Retrofit client
             */

            retrofitBuilder.client(okHttpClient);

            /**
             * create Retrofit
             */
            retrofit = retrofitBuilder.build();
            /**
             *create BaseApiService;
             */
            apiManager = retrofit.create(Api.class);

            return new ApiAction(callFactory, baseUrl, headers, parameters, apiManager);
        }
    }


    /**
     * ActionSubscriber
     *
     * @param <T>
     */
    class ActionObserverr<T> extends BaseObserver<ResponseBody> {

        private ResponseCallBack<T> callBack;

        public ActionObserverr(ResponseCallBack<T> callBack) {
            super();
            this.callBack = callBack;
        }

        @Override
        public void onComplete() {
            super.onComplete();
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                byte[] bytes = responseBody.bytes();
                String jsStr = new String(bytes);
                Log.d("OkHttp", "ResponseBody:" + jsStr);
                if (callBack != null) {
                    try {
                        /**
                         * if need parse baseRespone<T> use ParentType, if parse T use childType . defult parse baseRespone<T>
                         *
                         *  callBack.onSuccee((T) JSON.parseArray(jsStr, (Class<Object>) finalNeedType));
                         *  Type finalNeedType = needChildType;
                         */
                        Type type = callBack.getType();

                        Log.d("OkHttp", "type:" + type);
                        BaseResponse baseResponse = new Gson().fromJson(jsStr, type);
                        if (baseResponse == null) {
                            throw new NullPointerException();
                        }
                        if (baseResponse.getData() == null) {
                            throw new FormatException();
                        }

                        if (baseResponse.getCode() == 0) {
                            baseResponse = new Gson().fromJson(jsStr, type);
                            callBack.onSuccee((T) baseResponse);
                        } else {
                            String msg = baseResponse.getMsg() != null ? baseResponse.getMsg() : "系统繁忙";
                            ServerException serverException = new ServerException(baseResponse.getCode(), msg);
                            callBack.onError(ActionException.handleException(serverException));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (callBack != null) {
                            callBack.onError(ActionException.handleException(new FormatException()));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (callBack != null) {
                    callBack.onError(ActionException.handleException(e));
                }
            }

        }
    }

    /**
     * ResponseCallBack <T> Support your custom data model
     */
    public interface ResponseCallBack<T> {

        public void onCompleted();

        public abstract void onError(Throwable e);

        public abstract void onSuccee(T response);

        public Type getType();
    }
}


