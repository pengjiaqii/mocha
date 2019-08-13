package com.example.mocha.net.retrofit

import com.example.mocha.net.api.GirlApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:54
 * 功能 :
 */
class GirlOkhttpClient(private val okHttpClient:OkHttpClient) {
    private  var server:GirlApi

    init {
        val client = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(GirlApi.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        server = client.create(GirlApi::class.java)
    }
}