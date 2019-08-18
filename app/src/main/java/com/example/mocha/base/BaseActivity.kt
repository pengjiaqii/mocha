package com.example.baselib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/13 13:53
 * 功能 :
 */
abstract class BaseActivity : AppCompatActivity() {

    val TAG = this.javaClass.name


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEarliest()
        //绑定布局
        setContentView(getLayoutId())
        initData(savedInstanceState)
        initListener()
    }

    /**
     * 数据操作
     */
    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 一些监听和点击事件吧
     */
    abstract fun initListener()

    /**
     * 在布局之前可以去做的操作
     * 可重写可不重写，没定义成abstract
     */
    open fun initEarliest() {

    }

    /**
     * 布局id
     */
    abstract fun getLayoutId(): Int

}