package com.example.mocha.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.mocha.net.BaseResult
import com.example.mocha.net.response.GirlImgListData

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:45
 * 功能 :
 */
class GirlViewModel:ViewModel() {

    //LiveData
    private var girlData:MutableLiveData<BaseResult<GirlImgListData>> = MutableLiveData()

    //获取数据的方法，给外部调用
    fun getData():MutableLiveData<BaseResult<GirlImgListData>>{
        return girlData
    }


    //网络请求获取数据，网络请求这一块的主要功能就是放在ViewModel里面
    fun requestGirlImageData(type:String,pageIndex:Int){

    }

}