package com.example.mocha

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.transition.Explode
import android.view.Window
import android.view.WindowManager
import com.blankj.utilcode.util.ToastUtils
import com.example.baselib.BaseActivity
import com.example.mocha.fragment.GirlFragment
import com.example.mocha.fragment.GoodsListFragment
import com.example.mocha.fragment.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

//    private lateinit var mNavView: BottomNavigationView


    private lateinit var fragments: ArrayList<TagFragment>

    /**
     * 上次按下返回键的时间
     */
    private var lastBackPressTime = 0L

    /**
     * 当前fragment索引
     */
    private var currentFragmentIndex = -1


    companion object {


        fun actionTo(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }

    }

    override fun initEarliest() {
        super.initEarliest()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.enterTransition = Explode()
            window.exitTransition = Explode()
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
//        mNavView = binding.navView
        initFragments()
        initBottomNavigationView()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragments[0].fragment)
    }

    /**
     * 加入fragment
     */
    private fun initFragments() {
        fragments = ArrayList<TagFragment>().apply {
            add(TagFragment("news", NewsFragment.newInstance(null)))
            add(TagFragment("girl", GirlFragment.newInstance(null)))
            add(TagFragment("mine", GoodsListFragment.newInstance(null)))
        }

    }


    /**
     * 初始化BottomNavigationView
     */
    private fun initBottomNavigationView() {

    }

    override fun initListener() {
        bottom_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        bottom_nav_view.selectedItemId = bottom_nav_view.menu.getItem(0).itemId
    }

    override fun getLayoutId(): Int = R.layout.activity_main


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragments(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFragments(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragments(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * 使用show和hide切换fragment
     */
    private fun switchFragments(position: Int) {
        if (position == currentFragmentIndex) {
            return
        }
        val lastIndex = currentFragmentIndex
        currentFragmentIndex = position
        val tagFragment = fragments[position]
        supportFragmentManager.beginTransaction().apply {
            if (!tagFragment.fragment.isAdded) {
                this.add(R.id.fragment_container, tagFragment.fragment, tagFragment.tag)
            }
            if (lastIndex != -1) {
                this.hide(fragments[lastIndex].fragment)
            }
            this.show(tagFragment.fragment)
        }.commit()
    }


    override fun onBackPressed() {
        val currentBackTime = System.currentTimeMillis()
        if (currentBackTime - lastBackPressTime > 2000) {
            ToastUtils.showShort("再按一次返回键退出")
            lastBackPressTime = currentBackTime
        } else {
            finish()
        }
    }

    private inner class TagFragment(tag: String, fragment: Fragment) {
        val tag = tag
        val fragment = fragment
    }

}
