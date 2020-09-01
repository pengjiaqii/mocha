package com.example.baselibrary.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pengjiaqi on 2020/8/28 13:48.
 */
public class HeadersInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        //添加请求头的拦截器，一些公共参数
        Request.Builder requestBuilder = original.newBuilder()
                .header("token", "xxx")
                .header("token", "yyy");


        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
