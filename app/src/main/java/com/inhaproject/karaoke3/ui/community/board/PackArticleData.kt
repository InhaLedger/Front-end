package com.inhaproject.karaoke3.ui.community.board

data class PackArticleData(
    val packcontent: String,
    val packidx: Int,
    val packlist: String,
    val packtitle: String,
    val packwriter: Int,
    val upvote: Int,
    val downvote: Int,
    val packprice : String,
    val voteusers: Any,
    val userid : String,
    val already_vote : Int
)