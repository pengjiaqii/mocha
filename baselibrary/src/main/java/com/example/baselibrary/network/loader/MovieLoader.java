package com.example.baselibrary.network.loader;


import com.example.baselibrary.model.ArticleModel;
import com.example.baselibrary.model.GankBeautyResult;
import com.example.baselibrary.network.api.GankApi;
import com.example.baselibrary.network.base.BaseObjectLoader;
import com.example.baselibrary.util.NetworkUtil;

import io.reactivex.Observable;

/**
 * Created by pengjiaqi on 2020/8/28 15:27.
 */
public class MovieLoader extends BaseObjectLoader {

    private GankApi mGankService;

    public MovieLoader() {
        mGankService = NetworkUtil.newInstance().create(GankApi.class);
    }

    /**
     * 获取电影列表
     *
     * @param start
     * @param count
     * @return
     */
    public Observable<ArticleModel> getArticle() {
        return observe(mGankService.getArticle());
    }

    public Observable<GankBeautyResult> getBeauties(int number, int page) {
        return observe(mGankService.getBeauties(number, page));
    }
    //
    //    public Observable<String> getWeatherList(String cityId, String key) {
    //        return observe(mMovieService.getWeather(cityId, key))
    //                .map(new Func1<String, String>() {
    //                    @Override
    //                    public String call(String s) {
    //                        //可以处理对应的逻辑后在返回
    //                        return s;
    //                    }
    //                });
    //    }


}
