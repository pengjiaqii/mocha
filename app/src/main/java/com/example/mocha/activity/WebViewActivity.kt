package com.example.mocha.activity

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.example.baselib.BaseActivity
import com.example.mocha.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : BaseActivity() {

    private lateinit var url: String

    companion object {
        const val URL = "webview_url"

        fun actionTo(context: Context, url: String) {
            context.startActivity(Intent().apply {
                setClass(context, WebViewActivity::class.java)
                putExtra(URL, url)
            })
        }

    }


    override fun initData(savedInstanceState: Bundle?) {
        //toolbar的设置
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        webview_progressBar.progressDrawable = this.resources.getDrawable(R.drawable.color_progressbar, null)

        //webview初始化
        url = intent.getStringExtra(URL)
        initWebView()
        webview.loadUrl(url)

    }

    /**
     * 初始化webview
     */
    private fun initWebView() {
//        val param = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        binding.fl.addView(webview,param)
        val setting = webview.settings

        setting.javaScriptEnabled = false
        //设置自适应屏幕
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        //支持缩放
        setting.setSupportZoom(true)
        setting.builtInZoomControls = true
        setting.displayZoomControls = false

        setting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        //允许访问文件
        setting.allowFileAccess = true
        //自动加载图片
        setting.loadsImagesAutomatically = true
        //默认编码格式
        setting.defaultTextEncodingName = "utf-8"

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                webview_progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webview_progressBar.visibility = View.GONE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }
        }

        webview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                title?.let {
                    webview_toolbar.title = it
                }
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                webview_progressBar.progress = newProgress
                if (newProgress == 100) {
                    webview_progressBar.visibility = View.GONE
                }
            }

        }

    }

    override fun initListener() {
        webview_toolbar.setNavigationOnClickListener {
            if (webview.canGoBack()) {
                webview.goBack()
            } else {
                this@WebViewActivity.finish()
            }
        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }

    }

    override fun onDestroy() {
        webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        webview.clearHistory()
        webview.destroy()
        super.onDestroy()
    }


    override fun getLayoutId(): Int = R.layout.activity_webview


}
