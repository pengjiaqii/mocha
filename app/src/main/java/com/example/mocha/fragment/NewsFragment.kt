package com.example.mocha.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.mocha.R
import com.example.mocha.activity.WebViewActivity
import com.example.mocha.adpter.ArticleAdapter
import com.example.mocha.base.BaseFragment
import com.example.mocha.net.BaseStatus
import com.example.mocha.net.response.Article
import com.example.mocha.net.response.ArticleList
import com.example.mocha.viewmodel.NewsViewModel
import iammert.com.library.Status
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/15 13:12
 * 功能 :
 */
class NewsFragment : BaseFragment() {


    private lateinit var articleAdapter: ArticleAdapter

    private val newsViewModel: NewsViewModel
            by lazy { ViewModelProviders.of(this).get(NewsViewModel::class.java) }

    //是否正在刷新
    private var showRefresh = false

    //当前页码
    private var currentPage = 0

    //是否到结尾了
    private var isLastData = false

    companion object {
        fun newInstance(params: Bundle?): NewsFragment {
            val frag = NewsFragment()
            params?.let {
                frag.arguments = it
            }
            return frag
        }
    }

    override fun initView() {
        super.initView()
        //SwipeRefreshLayout
        news_refreshLayout.run {
            setOnRefreshListener {
                //下拉刷新
                currentPage = 0
                showRefresh = true
                newsViewModel.requestArticleData(currentPage)
                isRefreshing = true
            }
        }

        //recyclerview的一些配置
        news_recycleView.run {
            layoutManager = LinearLayoutManager(activity)
            articleAdapter = ArticleAdapter(context!!).apply {
                setOnItemViewClickListener(object : ArticleAdapter.OnItemClickListener {
                    override fun onItemClick(item: Article) {
                        //
                        WebViewActivity.actionTo(context!!, item.link)
                    }
                })
            }
            //rv滚动监听
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                        if (!isLastData) {
                            //静止状态，并且滑到rv最后一行的时候，开启加载更多
                            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition();
                            val visibleItemCount = layoutManager!!.childCount
                            val totalItemCount = layoutManager!!.itemCount
                            val state = recyclerView.scrollState
                            if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == RecyclerView.SCROLL_STATE_IDLE) {
                                currentPage++
                                newsViewModel.requestArticleData(currentPage)
                            }
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
//                    if (dy > 0 &&upto_list_top.visibility == View.VISIBLE) {
//                        //向上上拉隐藏
//                        upto_list_top.hide()
//                    } else if (dy < 0 &&upto_list_top.visibility != View.VISIBLE) {
//                        //向下下滑显示
//                        upto_list_top.show()
//                    }
                }
            })
            adapter = articleAdapter


        }
    }

    override fun initData() {
        super.initData()
        newsViewModel.getData().observe(this, object : Observer<BaseStatus<ArticleList>> {
            override fun onChanged(t: BaseStatus<ArticleList>?) {
                when (t?.status) {
                    BaseStatus.LOADING -> {
                        //loading
                        if (currentPage == 0 && !showRefresh) {
                            status_view.setStatus(Status.LOADING)
                        }
                    }
                    BaseStatus.SUCCESS -> {
                        news_refreshLayout.isRefreshing = false
                        //请求成功
                        status_view.setStatus(Status.COMPLETE)
                        if (currentPage == 0) {
                            //第一页
                            t.data?.datas?.let {
                                articleAdapter.update(ArrayList(it))
                            }
                        } else {
                            //从第二页开始是添加，不会清除之前的
                            t.data?.datas?.let {
                                articleAdapter.add(ArrayList(it))
                            }
                        }
                        if (t.data?.curPage == t.data?.pageCount) {
                            //最后一条数据，没有更多了
                            isLastData = true
                        }
                    }
                    BaseStatus.ERROR -> {
                        news_refreshLayout.isRefreshing = false
                        //请求失败
                        if (currentPage == 0) {
                            status_view.setStatus(Status.ERROR)
                        }
                    }
                }
            }
        })
        //网络请求
        newsViewModel.requestArticleData(currentPage)
    }

    override fun initListener() {
        super.initListener()

    }

    override fun getLayoutId(): Int = R.layout.fragment_news
}