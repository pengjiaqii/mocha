package com.example.mocha.adpter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mocha.R
import com.example.mocha.databinding.ItemFragGirlBinding
import com.example.mocha.util.GlideUtil

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 17:29
 * 功能 :
 */
class GirlRvAdapter(private val mContext: Context) : RecyclerView.Adapter<GirlRvAdapter.VH>() {


    private var onItemClickListener: OnItemClickListener? = null

    private var mData: ArrayList<String> = ArrayList()

    /**
     * itemview的点击事件
     */
    fun setOnItemViewClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    /**
     * 更新数据
     */
    fun update(data: ArrayList<String>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     */
    fun add(data: ArrayList<String>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize, mData.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = DataBindingUtil.inflate<ItemFragGirlBinding>(
            LayoutInflater.from(mContext),
            R.layout.item_frag_girl,
            parent,
            false
        )
        val holder = VH(binding)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView.tag as String)
        }
        return holder
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        viewHolder.bindData(mData[position])
        viewHolder.itemView.tag = mData[position]
    }


    class VH(binding: ViewDataBinding) : BaseRvHolder(binding.root) {

        fun bindData(item: String) {
            binding.let {
                if (it is ItemFragGirlBinding) {
                    it.girlAuthor.text = "作者：$item"
                    it.girlNum.text = "含${item}图"
                    GlideUtil.loadNormalImg(it.girlImg, item)
                }
            }

        }
    }


    interface OnItemClickListener {
        fun onItemClick(item: String)
    }
}