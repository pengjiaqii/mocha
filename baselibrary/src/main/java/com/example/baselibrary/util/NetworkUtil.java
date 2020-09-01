// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.baselibrary.util;


import com.example.baselibrary.network.interceptor.CommonLogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {
    private volatile static NetworkUtil sNetworkUtil;

    public static final String BASE_URL = "http://gank.io/api/";
    //    public static final String BASE_URL = "http://api.douban.com/";

    private static final int DEFAULT_TIMEOUT = 5;

    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;


    private final Retrofit mRetrofit;

    public static NetworkUtil newInstance() {
        if (sNetworkUtil == null) {
            synchronized (NetworkUtil.class) {
                if (sNetworkUtil == null) {
                    sNetworkUtil = new NetworkUtil();
                }
            }
        }
        return sNetworkUtil;
    }


    private NetworkUtil() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new CommonLogInterceptor(true));
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }


    //    private GankApi gankApi;

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    //        public GankApi getGankApi() {
    //        if (gankApi == null) {
    //            gankApi = retrofit.create(GankApi.class);
    //        }
    //        return gankApi;

}
