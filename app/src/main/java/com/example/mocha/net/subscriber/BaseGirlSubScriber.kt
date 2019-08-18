package com.example.mocha.net.subscriber

import android.util.Log
import com.blankj.utilcode.util.NetworkUtils
import com.example.mocha.net.response.GirlImgListData
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/14 9:40
 * 功能 :
 */
abstract class BaseGirlSubScriber:FlowableSubscriber<GirlImgListData> {


    private lateinit var mSubscription: Subscription

    override fun onComplete() {

    }

    override fun onSubscribe(s: Subscription) {
        Log.e("jade10","开始请求")
        mSubscription = s
        //开始请求的方法，给子类去实现的
        onStart()
        if(!NetworkUtils.isConnected()){
            mSubscription.cancel()
            onFail("请检查您的网络连接")
        }else{
            mSubscription.request(Long.MAX_VALUE)
        }
    }

    override fun onNext(data: GirlImgListData) {
        Log.e("jade10","data--->$data")
        if("ok" == data.status) onSuccess(data) else onFail("数据异常")
    }

    override fun onError(t: Throwable?) {
        onFail("网络请求服务异常")
        Log.e("jade10",t.toString())
    }

    /**
     * 请求开始
     */
    abstract fun onStart()

    /**
     * 请求失败异常的情况
     */
    abstract fun onFail(netErrorMsg: String)

    /**
     * 请求成功
     */
    abstract fun onSuccess(t: GirlImgListData)
}