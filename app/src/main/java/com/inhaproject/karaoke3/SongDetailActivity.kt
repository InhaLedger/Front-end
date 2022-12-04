package com.inhaproject.karaoke3

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.databinding.ActivitySongDetailBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.mynote.MyNoteData
import kotlinx.android.synthetic.main.activity_search_by_note.*
import kotlin.math.abs
import kotlinx.android.synthetic.main.activity_song_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySongDetailBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        val highNote = intent.getStringExtra("최고 음")
        Log.d("최고 음",highNote.toString())
        var octave = highNote.toString().substring(1..1)
        val note = highNote.toString().substring(0..0)
        var myOctave = ""
        var myNote = ""
        val noteList = listOf<String>("A","B","C","D","E","F","G")

        ReadSingTitle.text = intent.getStringExtra("노래 제목")
        ReadSinger.text = intent.getStringExtra("가수")
        ReadNo.text = intent.getStringExtra("번호").toString()
        ReadReleaseDate.text = intent.getStringExtra("발매일").toString()
        ReadComposerAndLyricist.text = intent.getStringExtra("작곡자") +
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

        api.showMyNote().enqueue(object : Callback<ArrayList<MyNoteData>>{
            override fun onResponse(
                call: Call<ArrayList<MyNoteData>>,
                response: Response<ArrayList<MyNoteData>>
            ) {
                if(response.code() == 200){
                    myNote = response.body()?.get(0)?.highNote.toString().substring(0..0)
                    myOctave = response.body()?.get(0)?.highNote.toString().substring(1..1)

                    if(octave == "#")
                        octave = highNote.toString().substring(2..2)

                    if (octave.toInt() < myOctave.toInt()){
                        ReadRange.text = "없음"
                    }
                    else {
                        if(note == myNote)
                            ReadRange.text = "없음"
                        else {
                            val distance = abs(noteList.indexOf(note)-noteList.indexOf(myNote))
                            ReadRange.text = distance.toString() + "key"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<MyNoteData>>, t: Throwable) {
                Toast.makeText(this@SongDetailActivity,"음역대 서버 연결 실패 " , Toast.LENGTH_SHORT).show()
                Log.d("나의 음역대 오류", t.message.toString())
            }

        })





    }
}