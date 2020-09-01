// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.baselibrary.network.api;


import com.example.baselibrary.model.ArticleModel;
import com.example.baselibrary.model.GanhuoModel;
import com.example.baselibrary.model.GankBeautyResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);

    //
    @GET("v2/categories/Article")
    Observable<ArticleModel> getArticle();


    @GET("categories/GanHuo")
    Observable<GanhuoModel> getGanHuo();
}
