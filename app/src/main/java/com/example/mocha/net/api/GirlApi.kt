package com.example.mocha.net.api

import com.example.mocha.net.response.GirlImgListData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GirlApi {

    companion object {
        @JvmField
        val baseUrl = "http://i.jandan.net/"

        @JvmField
        val TYPE_GIRL = "jandan.get_ooxx_comments"
    }

    @GET
    fun getDetailData(@Url url: String, @Query("oxwlxojflwblxbsapi") type: String,
            @Query("page") page: Int): Flowable<GirlImgListData>
}