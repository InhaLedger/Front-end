package com.inhaproject.karaoke3.ui.community.board

data class ArticleModel(
    val userId: String,
    val title: String,
    val createdAt: Long,
    val price: String,
    val content: String,
) {
    constructor(): this("","",0,"","")
}