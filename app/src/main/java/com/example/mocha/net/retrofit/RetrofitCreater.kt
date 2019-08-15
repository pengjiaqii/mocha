package com.example.mocha.net.retrofit

import com.example.mocha.MochaApplication
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.safframework.http.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/14 13:38
 * 功能 :
 */
object RetrofitCreater {


    private val okHttpClient: OkHttpClient
    /**
     * 默认超时时间
     */
    private const val DEFAULT_TIMEOUT = 15L


//    private var retrofit: Retrofit

//    private val okHttpClient: OkHttpClient

    init {
        //okhttp
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MochaApplication.INSTANCE))


        val loggingInterceptor = LoggingInterceptor.Builder()
                .loggable(true) // TODO: 发布到生产环境需要改成false
                .request()
                .requestTag("RequestLog")
                .response()
                .responseTag("ResponseLog")
                //.hideVerticalLine()// 隐藏竖线边框
                .build()

        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(loggingInterceptor)
                .build()

        //retrofit

    }


    /**
     * 获取Retrofit对象
     *
     * @return
     */
//    fun getRetrofitService(): Retrofit {
//        retrofit = Retrofit.Builder()
//                .client(okHttpBuilder.build())
//                .baseUrl(GirlApiService.baseUrl)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//    }

    /**
     * 获取Service，因为这里用了很多不同的api,所以得区分service
     */
    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(serviceClass)
    }


}