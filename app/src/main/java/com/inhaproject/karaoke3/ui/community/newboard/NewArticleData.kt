package com.inhaproject.karaoke3.ui.community.newboard

data class NewArticleData(
    val new_album: String,
    val new_boardcontent: String,
    val new_boardtitle: String,
    val new_composer: String,
    val new_imageurl: String,
    val new_lyricist: String,
    val new_no: Int,
    val new_releasedate: String,
    val new_singer: String,
    val new_title: String,
    val new_writer: String,
    val newidx: Int,
    val userid: String,
    val upvote: Int,
    val downvote: Int,
    val already_vote : Int
)