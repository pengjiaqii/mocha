package com.example.mocha.net.response

data class ArticleStatus(
        val data: ArticleList,
        val errorCode: Int,
        val errorMsg: String
)