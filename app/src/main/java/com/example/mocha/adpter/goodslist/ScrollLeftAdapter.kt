package com.example.mocha.adpter.goodslist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mocha.R

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/16 0016 21:49
 * 功能 :
 */
class ScrollLeftAdapter(private val mContext: Context, private var mDatas: ArrayList<String>)
    : RecyclerView.Adapter<ScrollLeftAdapter.ScrollLeftViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ScrollLeftViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_scroll_left, parent, false)
        val holder = ScrollLeftViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(articleViewHolder: ScrollLeftViewHolder, i: Int) {

    }

    override fun getItemCount(): Int = mDatas.size

    inner class ScrollLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var left_text = itemView.findViewById<TextView>(R.id.left_text)

    }
}
