package com.example.mocha.net

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/14 14:10
 * 功能 :
 */
object Composer {
    fun <T> compose(): FlowableTransformer<T, T> {
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}