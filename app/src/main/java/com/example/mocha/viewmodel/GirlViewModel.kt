package com.example.mocha.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.mocha.net.BaseStatus
import com.example.mocha.net.api.ApiUtil
import com.example.mocha.net.response.GirlImgListData
import com.example.mocha.net.subscriber.BaseGirlSubScriber
import com.example.mocha.repository.GirlRepository

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:45
 * 功能 :
 */
class GirlViewModel : ViewModel() {

    //LiveData
    private var girlData: MutableLiveData<BaseStatus<GirlImgListData>> = MutableLiveData()
    private val repository by lazy { GirlRepository() }
    //获取数据的方法，给外部调用
    fun getData(): MutableLiveData<BaseStatus<GirlImgListData>> {
        return girlData
    }


    //网络请求获取数据，网络请求这一块的主要功能就是放在ViewModel里面
    fun requestGirlImageData(type: String, pageNum: Int) {
        ApiUtil.getGirlsImage(type, pageNum, object : BaseGirlSubScriber() {
            override fun onStart() {
                girlData.value = BaseStatus.loading(null)
            }

            override fun onFail(netErrorMsg: String) {
                girlData.value = BaseStatus.error(null, netErrorMsg)
            }

            override fun onSuccess(data: GirlImgListData) {
                girlData.value = BaseStatus.success(data)
            }

        })

//        launch {
//            val result = withContext(Dispatchers.IO) {
//                repository.getGirlsImage(type, pageNum)
//            }
//        }

    }

}