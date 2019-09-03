package com.example.mocha.adpter.goodslist

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mocha.R
import com.example.mocha.data.ClassEntity


/**
 * 作者 : mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/16 0016 21:49
 * 功能 :
 */
class ScrollLeftAdapter(private val mContext: Context, private var mDatas: ArrayList<ClassEntity>)
    : RecyclerView.Adapter<ScrollLeftAdapter.ScrollLeftViewHolder>() {

    private var position: Int = 0


    private var onItemClickListener: OnItemClickListener? = null
    /**
     * itemview的点击事件
     */
    fun setOnItemViewClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ScrollLeftViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_scroll_left, parent, false)
        val holder = ScrollLeftViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(articleViewHolder: ScrollLeftViewHolder, i: Int) {

        articleViewHolder.itemView.tag = mDatas[i]

        articleViewHolder.left_text.text = mDatas[i].name
        articleViewHolder.left_text.setTextColor(if (i == position) Color.parseColor("#8470FF") else Color.parseColor("#363636"))

        articleViewHolder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(mDatas[i], i)
        }
    }

    fun setSelection(pos: Int) {
        this.position = pos
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDatas.size

    inner class ScrollLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var left_text = itemView.findViewById<TextView>(com.example.mocha.R.id.left_text)

    }

    interface OnItemClickListener {
        fun onItemClick(item: ClassEntity, i: Int)
    }
}
