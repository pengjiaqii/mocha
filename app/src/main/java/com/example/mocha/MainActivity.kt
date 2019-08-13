package com.example.mocha

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.example.baselib.BaseActivity
import com.example.mocha.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

//    private lateinit var mNavView: BottomNavigationView

    override fun initData(savedInstanceState: Bundle?) {
//        mNavView = binding.navView
        initFragments()
        initBottomNavigationView()
    }

    /**
     * 初始化bnv
     */
    private fun initBottomNavigationView() {


    }

    /**
     * 加入fragment
     */
    private fun initFragments() {


    }

    override fun initListener() {
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun getLayoutId(): Int = R.layout.activity_main


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
