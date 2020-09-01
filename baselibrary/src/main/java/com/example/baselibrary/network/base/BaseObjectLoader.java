package com.example.baselibrary.network.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pengjiaqi on 2020/8/28 15:17.
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 */
public class BaseObjectLoader {

    /**
     *
     * @param observable
     * @param <T>
     * @return
     */
    protected  <T> Observable<T> observe(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
