package com.example.mocha.activity

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.baselib.BaseActivity
import com.example.mocha.MainActivity
import com.example.mocha.R

class SplashActivity : BaseActivity() {

    override fun initEarliest() {
        super.initEarliest()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
    }


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ MainActivity.actionTo(this@SplashActivity) }, 2000)

    }

    override fun initListener() {

    }

    override fun getLayoutId(): Int = R.layout.activity_splash


}
