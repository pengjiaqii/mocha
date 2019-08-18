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
class ScrollRightAdapter(private val mContext: Context, private var mDatas: ArrayList<String>)
    : RecyclerView.Adapter<ScrollRightAdapter.ScrollRightViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ScrollRightViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_scroll_right, viewGroup, false)
        val holder = ScrollRightViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(scrollRightViewHolder: ScrollRightViewHolder, i: Int) {

    }

    override fun getItemCount(): Int = mDatas.size

    inner class ScrollRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var right_text = itemView.findViewById<TextView>(R.id.right_text)

    }
}
