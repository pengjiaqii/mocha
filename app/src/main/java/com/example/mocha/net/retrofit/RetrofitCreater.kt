package com.example.mocha.net.retrofit

import com.example.mocha.MochaApplication
import com.example.mocha.net.api.GirlApiService
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
    /**
     * 默认超时时间
     */
    private const val DEFAULT_TIMEOUT = 15L


    private var retrofit: Retrofit

    init {
        //okhttp
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MochaApplication.INSTANCE))
        val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(cookieJar)

        val loggingInterceptor = LoggingInterceptor.Builder()
                .loggable(true) // TODO: 发布到生产环境需要改成false
                .request()
                .requestTag("RequestLog")
                .response()
                .responseTag("ResponseLog")
                //.hideVerticalLine()// 隐藏竖线边框
                .build()

        okHttpBuilder.addInterceptor(loggingInterceptor)

        //retrofit
        retrofit = Retrofit.Builder()
                .client(okHttpBuilder.build())
                .baseUrl(GirlApiService.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()


    }


    /**
     * 获取Retrofit对象
     *
     * @return
     */
    fun getRetrofit(): Retrofit = retrofit


//    fun getGirlRequest(): GirlOkhttpClient? {
//        if (girlRequest == null) {
//            girlRequest = GirlOkhttpClient(mOkHttpClient)
//        }
//        return girlRequest
//    }
}