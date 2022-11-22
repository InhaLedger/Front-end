package com.inhaproject.karaoke3

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.databinding.ActivitySongDetailBinding
import kotlinx.android.synthetic.main.activity_song_detail.*

class SongDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        ReadSingTitle.text = "제목: "+ intent.getStringExtra("노래 제목")
        ReadSinger.text = "가수 : " + intent.getStringExtra("가수")
        ReadNo.text = "노래방 번호 : " + intent.getStringExtra("번호").toString()
        ReadReleaseDate.text = "발매일 : " + intent.getStringExtra("발매일").toString()
        ReadComposerAndLyricist.text = "작사 / 작곡 : " +intent.getStringExtra("작곡자") +
                " / " + intent.getStringExtra("작사자")

        Log.d("앨범 이미지 유무",intent.getStringExtra("앨범 이미지").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("앨범 이미지"))) {
            Glide.with(this).load(
                intent.getStringExtra("앨범 이미지")
            ).into(ReadImageView)
        }

        else ReadImageView.setImageResource(R.drawable.no_pictures)

        Log.d("앨범 유무",intent.getStringExtra("앨범 제목").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("앨범 제목"))) {
            ReadAlbumName.text = intent.getStringExtra("앨범 제목")
        }

        else ReadAlbumName.text = "앨범 정보가 없습니다."

    }
}