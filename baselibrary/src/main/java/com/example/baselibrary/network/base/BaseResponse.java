package com.example.baselibrary.network.base;

/**
 * Created by pengjiaqi on 2020/8/28 14:09.
 */
public class BaseResponse<T> {
    public int status;
    public String message;
    public T data;

    public boolean isSuccess() {
        return status == 200;
    }
}
