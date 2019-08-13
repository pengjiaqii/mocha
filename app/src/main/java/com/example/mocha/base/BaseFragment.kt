package com.example.mocha.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 16:40
 * 功能 :
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {


    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        initView()
        initListener()
        initData()
        return binding.root
    }


    open fun initView() {


    }

    open fun initListener() {


    }


    open fun initData() {

    }


    abstract fun getLayoutId(): Int


}