package com.example.mocha.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.mocha.R
import com.example.mocha.adpter.GirlRvAdapter
import com.example.mocha.base.BaseFragment
import com.example.mocha.databinding.FragmentGirlBinding
import com.example.mocha.viewmodel.GirlViewModel

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 17:06
 * 功能 :
 */
class GirlFragment : BaseFragment<FragmentGirlBinding>() {


    private lateinit var girlRvAdapter: GirlRvAdapter
    //grid列数
    private val spanCount = 2

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

        }
        binding.girlRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })
    }

    override fun initData() {
        //rv列表的常规操作
        girlRvAdapter = GirlRvAdapter(context!!)
        girlRvAdapter.setOnItemViewClickListener(object :GirlRvAdapter.OnItemClickListener{
            override fun onItemClick(item: String) {

            }
        })
        binding.girlRv.layoutManager  = GridLayoutManager(context!!,spanCount)
        binding.girlRv.adapter = girlRvAdapter

        //viewmodel，网络请求
        ViewModelProviders.of(this).get(GirlViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_girl

}