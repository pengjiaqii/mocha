package com.example.mocha.adpter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mocha.R
import com.example.mocha.net.response.GirlImgComment
import com.example.mocha.util.GlideUtil

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 17:29
 * 功能 :
 */
class GirlRvAdapter(private val mContext: Context) : RecyclerView.Adapter<GirlRvAdapter.GirlViewHolder>() {


    private var onItemClickListener: OnItemClickListener? = null

    private var mData: ArrayList<GirlImgComment> = ArrayList()

    /**
     * itemview的点击事件
     */
    fun setOnItemViewClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    /**
     * 更新数据
     */
    fun update(data: ArrayList<GirlImgComment>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     */
    fun add(data: ArrayList<GirlImgComment>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize, mData.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_frag_girl, parent, false)
        val holder = GirlViewHolder(view)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView.tag as GirlImgComment)
        }
        return holder
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: GirlViewHolder, position: Int) {
        val item = mData[position]
        viewHolder.itemView.tag = item

        viewHolder.girl_author.text = "作者：${item.comment_author}"
        viewHolder.girl_num.text = "含${item.pics.size}图"

        GlideUtil.loadNormalImg(viewHolder.girl_img, item.pics[0])
    }


    inner class GirlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var girl_author = itemView.findViewById<TextView>(R.id.girl_author)
        var girl_num = itemView.findViewById<TextView>(R.id.girl_num)
        var girl_img = itemView.findViewById<ImageView>(R.id.girl_img)


    }


    interface OnItemClickListener {
        fun onItemClick(item: GirlImgComment)
    }
}