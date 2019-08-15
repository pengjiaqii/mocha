package com.example.mocha.adpter

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mocha.R
import com.example.mocha.net.response.Article

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/15 14:30
 * 功能 :
 */
class ArticleAdapter(private val mContext: Context) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var isLighting: Boolean = false

    private var mData: ArrayList<Article> = ArrayList()

    private var onItemClickListener: OnItemClickListener? = null
    /**
     * itemview的点击事件
     */
    fun setOnItemViewClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }


    /**
     * 更新数据
     */
    fun update(data: ArrayList<Article>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     */
    fun add(data: ArrayList<Article>) {
        val oldSize = mData.size
        mData.addAll(data)
        notifyItemRangeInserted(oldSize, mData.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_frag_article, parent, false)
        val holder = ArticleViewHolder(view)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView.tag as Article)
        }
        return holder
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(viewHolder: ArticleViewHolder, position: Int) {
        val item = mData[position]
        viewHolder.itemView.tag = item



        viewHolder.article_title.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(item.title)
        }
        viewHolder.article_author.text = item.author

        viewHolder.article_tag.text = "${item.superChapterName ?: ""} ${item.chapterName}"

        viewHolder.article_time.text = item.niceDate

        viewHolder.article_star.setImageResource(
                if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal
        )

        viewHolder.article_star.setOnClickListener {
            viewHolder.article_star.setImageResource(if (isLighting) {
                R.drawable.timeline_like_normal
            } else {
                R.drawable.timeline_like_pressed
            })
            isLighting = !isLighting
        }
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var article_author = itemView.findViewById<TextView>(R.id.article_author)
        var article_tag = itemView.findViewById<TextView>(R.id.article_tag)
        var article_title = itemView.findViewById<TextView>(R.id.article_title)
        var article_time = itemView.findViewById<TextView>(R.id.article_time)
        var article_star = itemView.findViewById<ImageView>(R.id.article_star)

    }

    interface OnItemClickListener {
        fun onItemClick(item: Article)
    }

}