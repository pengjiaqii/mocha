package com.example.mocha.net.api

import com.example.mocha.net.response.ArticleStatus
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/15 15:34
 * 功能 :
 */
interface WanAndroidService {

    companion object {
        @JvmField
        val baseUrl = "https://www.wanandroid.com/"

    }

    @GET("article/list/{page}/json")
    fun getHomeArticles(@Path("page") page: Int): Flowable<ArticleStatus>
}