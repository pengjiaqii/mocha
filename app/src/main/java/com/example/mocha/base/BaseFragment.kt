package com.example.mocha.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 16:40
 * 功能 :
 */
abstract class BaseFragment : Fragment() {


    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(getLayoutId(), container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initListener()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }


    open fun initView() {


    }

    open fun initListener() {


    }


    open fun initData() {
        Log.d("jade10", "${this.javaClass.name}initData")
    }


    abstract fun getLayoutId(): Int


}