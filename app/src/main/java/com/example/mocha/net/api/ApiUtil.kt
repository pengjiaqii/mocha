package com.example.mocha.net.api

import com.example.mocha.net.Composer
import com.example.mocha.net.retrofit.RetrofitCreater
import com.example.mocha.net.subscriber.BaseGirlSubScriber

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/14 13:33
 * 功能 :
 */
object ApiUtil {

    init {

    }


    fun getGrilsImage(type:String,page:Int,subscriber: BaseGirlSubScriber){
        RetrofitCreater.getRetrofit().create(GirlApiService::class.java).getDetailData(GirlApiService.baseUrl,type,page)
                .compose(Composer.compose())
                .subscribe(subscriber)
    }

}