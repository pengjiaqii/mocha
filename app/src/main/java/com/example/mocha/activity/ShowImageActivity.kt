package com.example.mocha.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.baselib.BaseActivity
import com.example.mocha.R
import com.example.mocha.adpter.ShowImageAdapter
import kotlinx.android.synthetic.main.activity_show_image.*

class ShowImageActivity : BaseActivity() {


    companion object {

        const val IMAGE_URL_LIST = "showimageactivity_image_url_list"

        fun actionTo(context: Context, imageUrls: ArrayList<String>) {
            context.startActivity(Intent().apply {
                setClass(context, ShowImageActivity::class.java)
                putExtra(IMAGE_URL_LIST, imageUrls)
            })
        }

    }

    override fun initEarliest() {

    }


    override fun initData(savedInstanceState: Bundle?) {
        val imageUrlList = intent.getStringArrayListExtra(IMAGE_URL_LIST)
        show_image_vp.adapter = ShowImageAdapter(this, imageUrlList)
    }

    override fun initListener() {

    }

    override fun getLayoutId(): Int = R.layout.activity_show_image


}
