package com.example.mocha.net.retrofit

import com.example.mocha.MochaApplication
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.safframework.http.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
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

    private val mOkHttpClient: OkHttpClient

    private var girlRequest: GirlOkhttpClient? = null

    init {
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

        mOkHttpClient = okHttpBuilder.build()

    }

    fun getGirlRequest(): GirlOkhttpClient? {
        if(girlRequest == null){
            girlRequest = GirlOkhttpClient(mOkHttpClient)
        }
        return girlRequest
    }
}