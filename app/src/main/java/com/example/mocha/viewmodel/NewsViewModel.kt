package com.example.mocha.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.mocha.net.BaseStatus
import com.example.mocha.net.api.ApiUtil
import com.example.mocha.net.response.ArticleList
import com.example.mocha.net.subscriber.BaseArticleSubscriber

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:45
 * 功能 :
 */
class NewsViewModel : ViewModel() {

    //LiveData
    private var articleData: MutableLiveData<BaseStatus<ArticleList>> = MutableLiveData()

    //获取数据的方法，给外部调用
    fun getData(): MutableLiveData<BaseStatus<ArticleList>> {
        return articleData
    }


    //网络请求获取数据，网络请求这一块的主要功能就是放在ViewModel里面
    fun requestArticleData(pageNum: Int) {
        ApiUtil.getArticleList(pageNum, object : BaseArticleSubscriber() {
            override fun onStart() {
                articleData.value = BaseStatus.loading(null)
            }

            override fun onFail(netErrorMsg: String) {
                articleData.value = BaseStatus.error(null, netErrorMsg)
            }

            override fun onSuccess(data: ArticleList) {
                articleData.value = BaseStatus.success(data)
            }

        })

//        launch {
//            val result = withContext(Dispatchers.IO) {
//                repository.getGirlsImage(type, pageNum)
//            }
//        }

    }

}