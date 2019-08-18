package com.example.mocha.util

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mocha.R

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/13 18:30
 * 功能 :
 */
object GlideUtil {
    /**
     * 加载普通图像
     */
    fun loadNormalImg(imageView: ImageView, url:String){
        GlideApp.with(imageView.context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.default_img)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}