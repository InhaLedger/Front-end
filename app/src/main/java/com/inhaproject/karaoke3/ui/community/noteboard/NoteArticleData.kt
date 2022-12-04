package com.inhaproject.karaoke3.ui.community.noteboard

data class NoteArticleData(
    val highNote: String,
    val lowNote: String,
    val note_content: String,
    val noteidx: Int,
    val note_no: Int,
    val note_title: String,
    val note_writer: Int,
    val voteusers: String,
    val userid : String,
    val title : String,
    val singer : String,
    val upvote: Int,
    val downvote: Int,
    val already_vote : Int
)