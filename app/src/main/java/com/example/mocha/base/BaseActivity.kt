package com.example.baselib

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 13:53
 * 功能 :
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val TAG = this.javaClass.name

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEarliest(savedInstanceState)
        //绑定布局
        binding = DataBindingUtil.setContentView(this, getLayoutId())
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
    open fun initEarliest(savedInstanceState: Bundle?) {

    }

    /**
     * 布局id
     */
    abstract fun getLayoutId(): Int

}