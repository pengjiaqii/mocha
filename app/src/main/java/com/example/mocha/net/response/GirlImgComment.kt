package com.example.mocha.net.response

/**
 * 作者 : Mocha
 * 邮箱 : robotjiaqi@163.com
 * 日期 : 2019/8/13 18:49
 * 功能 :
 */
data class GirlImgComment( val comment_ID: String,
                           val comment_post_ID: String,
                           val comment_author: String,
                           val comment_date: String,
                           val comment_date_gmt: String,
                           val comment_content: String,
                           val user_id: String,
                           val vote_positive: String,
                           val vote_negative: String,
                           val sub_comment_count: String,
                           val text_content: String,
                           val pics: List<String>)