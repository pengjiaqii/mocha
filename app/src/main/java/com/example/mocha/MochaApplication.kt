package com.example.mocha

import android.app.Activity
import android.app.Application
import android.os.Handler
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/14 13:50
 * 功能 :
 */
class MochaApplication:Application() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    companion object {

        var INSTANCE:MochaApplication by Delegates.notNull()
        var mUIHandler: Handler by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        INSTANCE = this
        mUIHandler = Handler()
        Utils.init(this)
//        BigImageViewer.initialize(GlideImageLoader.with(this))

    }


}