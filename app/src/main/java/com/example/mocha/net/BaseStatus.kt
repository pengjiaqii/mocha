package com.example.mocha.net

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:47
 * 功能 :
 */
class BaseStatus<T : Any?>(val status: Int, val data: T?, val msg: String?) {

    companion object {
      const val ERROR = 0
      const val LOADING = 1
      const val SUCCESS = 2

        fun <T> success(data: T): BaseStatus<T> {
            return BaseStatus(SUCCESS, data, null)
        }

        fun <T> error(data: T?, msg: String?): BaseStatus<T> {
            return BaseStatus(ERROR, data, msg)
        }

        fun <T> loading(data: T?): BaseStatus<T> {
            return BaseStatus(LOADING, data, null)
        }
    }
}