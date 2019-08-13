package com.example.mocha.net

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:47
 * 功能 :
 */
class BaseResult<T : Any?>(val status: Int, val data: T?, val msg: String?) {

    companion object {
        private const val ERROR = 0
        private const val LOADING = 1
        private const val SUCCESS = 2

        fun <T> success(data: T): BaseResult<T> {
            return BaseResult(SUCCESS, data, null)
        }

        fun <T> error(data: T?, msg: String?): BaseResult<T> {
            return BaseResult(ERROR, data, msg)
        }

        fun <T> loading(data: T?): BaseResult<T> {
            return BaseResult(LOADING, data, null)
        }
    }
}