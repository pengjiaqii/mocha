package com.example.mocha.net.api

import com.example.mocha.net.Composer
import com.example.mocha.net.retrofit.RetrofitCreater
import com.example.mocha.net.subscriber.BaseArticleSubscriber
import com.example.mocha.net.subscriber.BaseGirlSubScriber

/**
 * 作者 : mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/14 13:33
 * 功能 :
 */
object ApiUtil {

    init {

    }


    fun getGirlsImage(type: String, page: Int, subscriber: BaseGirlSubScriber) {
        RetrofitCreater.getService(GirlApiService::class.java, GirlApiService.baseUrl)
                .getDetailData(GirlApiService.baseUrl, type, page)
                .compose(Composer.compose())
                .subscribe(subscriber)
    }

    fun getArticleList(page: Int, subscriber: BaseArticleSubscriber) {
        RetrofitCreater.getService(WanAndroidService::class.java, WanAndroidService.baseUrl)
                .getHomeArticles(page)
                .compose(Composer.compose())
                .subscribe(subscriber)
    }

}