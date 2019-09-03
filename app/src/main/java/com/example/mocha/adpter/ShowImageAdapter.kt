package com.example.mocha.adpter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.mocha.R
import com.example.mocha.util.GlideUtil

/**
 * 作者 : mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/15 10:08
 * 功能 : viewpager 的adapter
 */
class ShowImageAdapter(private var context: Context, private var imageUrls: ArrayList<String>) : PagerAdapter() {


    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        val url = imageUrls[position]
        imageView.setBackgroundColor(R.color.black)
//        imageView.showImage(Uri.parse(url))
        GlideUtil.loadNormalImg(imageView, url)
        imageView.setOnClickListener {

        }

//        imageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_CROP)
//        imageView.setImageViewFactory(GlideImageViewFactory())
        container.addView(imageView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        return imageView
    }

    override fun getCount(): Int = imageUrls.size


}