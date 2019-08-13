package com.example.mocha.net.response

/**
 * 作者 : pengjiaqi
 * 邮箱 : pengjiaqi@richinfo.cn
 * 日期 : 2019/8/13 18:49
 * 功能 :
 */
data class GirlImgListData( val status: String,
                       val current_page: Int,
                       val total_comments: Int,
                       val page_count: Int,
                       val count: Int,
                       val comments: List<GirlImgComment>)