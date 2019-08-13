package com.example.mocha.adpter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 17:30
 * 功能 :
 */
open class BaseRvHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    lateinit var binding: ViewDataBinding

    open fun bind(variableId:Int,value:Any){
        binding.setVariable(variableId,value)
        binding.executePendingBindings()
    }

}