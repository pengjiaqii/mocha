package com.example.mocha.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.mocha.R
import com.example.mocha.adpter.GirlRvAdapter
import com.example.mocha.base.BaseFragment
import com.example.mocha.databinding.FragmentGirlBinding
import com.example.mocha.net.BaseStatus
import com.example.mocha.net.api.GirlApiService
import com.example.mocha.net.response.GirlImgComment
import com.example.mocha.net.response.GirlImgListData
import com.example.mocha.viewmodel.GirlViewModel
import iammert.com.library.Status

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 17:06
 * 功能 :
 */
class GirlFragment : BaseFragment<FragmentGirlBinding>() {

    private val girlViewModel: GirlViewModel
            by lazy { ViewModelProviders.of(this).get(GirlViewModel::class.java) }

    private lateinit var girlRvAdapter: GirlRvAdapter
    //grid列数
    private val spanCount = 2
    //页面索引，因为加了分页加载
    private var pageIndex: Int = 1
    //是否正在刷新
    private var showRefresh = false

    //是否到结尾了
    private var isLastData = false

    companion object {
        fun newInstance(params: Bundle?): GirlFragment {
            var frag = GirlFragment()
            params?.let {
                frag.arguments = it
            }
            return frag
        }
    }

//    override fun initView() {
//
//    }

    override fun initListener() {
        binding.girlSrl.setOnRefreshListener {
            pageIndex = 1
            showRefresh =true
            girlViewModel.requestGirlImageData(GirlApiService.TYPE_GIRL, pageIndex)
        }
        binding.girlRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPos = 0
            var firstVisibleItemPos = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isLastData) {
                    //滑倒rv最后一行的时候，开启加载更多
                    lastVisibleItemPos = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    firstVisibleItemPos = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItemPos != 0 && lastVisibleItemPos >= girlRvAdapter.itemCount - spanCount * 2) {
                        pageIndex++
                        girlViewModel.requestGirlImageData(GirlApiService.TYPE_GIRL, pageIndex)
                    }
                }else if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    binding.uptoListTop.show()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.uptoListTop.visibility == View.VISIBLE) {
                    //向上上拉隐藏
                    binding.uptoListTop.hide()
                } else if (dy < 0 && binding.uptoListTop.visibility != View.VISIBLE) {
                    //向下下滑显示
                    binding.uptoListTop.show()
                }
            }
        })

        //点击滑动到第一屏顶部
        binding.uptoListTop.setOnClickListener {
            binding.girlRv.smoothScrollToPosition(0)
        }
    }

    override fun initData() {
        super.initData()
        //rv列表的常规操作
        girlRvAdapter = GirlRvAdapter(context!!)
        girlRvAdapter.setOnItemViewClickListener(object : GirlRvAdapter.OnItemClickListener {
            override fun onItemClick(item: GirlImgComment) {

            }
        })
        binding.girlRv.layoutManager = GridLayoutManager(context!!, spanCount)
        binding.girlRv.adapter = girlRvAdapter
        //viewmodel，网络请求
        //获取MutableLiveData ，并“观察”？这个词不知道准不准确
        girlViewModel.getData().observe(this, object : Observer<BaseStatus<GirlImgListData>> {
            override fun onChanged(t: BaseStatus<GirlImgListData>?) {
                when (t?.status) {
                    BaseStatus.LOADING -> {
                        //loading
                        if (pageIndex == 1 && !showRefresh) {
                            binding.statusView.setStatus(Status.LOADING)
                        }
                    }
                    BaseStatus.SUCCESS -> {
                        binding.girlSrl.isRefreshing = false
                        //请求成功
                        binding.statusView.setStatus(Status.COMPLETE)
                        if (pageIndex == 1) {
                            //第一页
                            t.data?.comments?.let {
                                girlRvAdapter.update(ArrayList(it))
                            }
                        } else {
                            //从第二页开始是添加，不会清除之前的
                            t.data?.comments?.let {
                                girlRvAdapter.add(ArrayList(it))
                            }
                        }
                        if (t.data?.current_page == t.data?.page_count) {
                            //最后一条数据，没有更多了
                            isLastData = true
                        }
                    }
                    BaseStatus.ERROR -> {
                        binding.girlSrl.isRefreshing = false
                        //请求失败
                        if (pageIndex == 1) {
                            binding.statusView.setStatus(Status.ERROR)
                        }
                    }
                }
            }
        })
        //正在加载数据的方法
        girlViewModel.requestGirlImageData(GirlApiService.TYPE_GIRL, pageIndex)
    }

    override fun getLayoutId(): Int = R.layout.fragment_girl

}