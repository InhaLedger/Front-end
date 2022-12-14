package com.inhaproject.karaoke3.ui.community.noteboard

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityReadNoteBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadNoteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReadNoteBinding

    private val api = RetroInterface.create()

    var data : NoteArticleData? = null

    var already = 0

    var temp = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_note)

        val noteTitle = findViewById<TextView>(R.id.note_Title)
        val noteWriter = findViewById<TextView>(R.id.note_Writer)
        val noteHighNote = findViewById<TextView>(R.id.noteReadHighNote)
        val noteLowNote = findViewById<TextView>(R.id.noteReadLowNote)
        val noteContent = findViewById<TextView>(R.id.note_Content)
        val noteLikeButton = findViewById<ImageButton>(R.id.noteLike)
        val noteDislikeButton = findViewById<ImageButton>(R.id.noteDislike)
        val noteLike = findViewById<TextView>(R.id.note_Like_TextView)
        val noteDislike = findViewById<TextView>(R.id.note_DisLike_TextView)
        val songTitle = findViewById<TextView>(R.id.noteReadSongTitle)
        val singer = findViewById<TextView>(R.id.noteReadSinger)

        val noteIdx = intent.getStringExtra("음역대 게시물 번호")
        Log.d("음역대 게시물 번호",noteIdx.toString())

        Log.d("추천 유무",intent.getStringExtra("추천 수").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("추천 수"))) {
            noteLike.text = intent.getStringExtra("추천 수").toString()
        }

        else noteLike.text = "0"

        Log.d("비추천 유무",intent.getStringExtra("비추천 수").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("비추천 수"))) {
            noteDislike.text = intent.getStringExtra("비추천 수").toString()
        }

        else noteDislike.text = "0"

        api.noteRead(noteIdx.toString()).enqueue(object : Callback<ArrayList<NoteArticleData>> {
            override fun onResponse(
                call: Call<ArrayList<NoteArticleData>>,
                response: Response<ArrayList<NoteArticleData>>
            ) {
                if (response.body().toString().isNotEmpty()) {
                    response.body().let {
                        noteTitle.text = response.body()?.get(0)?.note_title
                        noteWriter.text = "작성자 : " + response.body()?.get(0)?.note_writer
                        songTitle.text = "노래 제목 : " + intent.getStringExtra("노래 제목")
                        singer.text = "가수 : " + intent.getStringExtra("가수")
                        noteHighNote.text = "최고 음: " + response.body()?.get(0)?.highNote
                        noteLowNote.text = "최저 음: " + response.body()?.get(0)?.lowNote
                        noteContent.text = response.body()?.get(0)?.note_content
                        already = response.body()?.get(0)?.already_vote!!
                    }
                    noteLikeButton.setOnClickListener{
                        this.let {
                            if(already == 1 || temp == 1){
                                Toast.makeText(this@ReadNoteActivity,"이미 투표한 게시물입니다.", Toast.LENGTH_SHORT).show()
                            }

                            else {
                                api.noteVote(noteIdx.toString(), "up")
                                    .enqueue(object : Callback<String> {
                                        override fun onResponse(
                                            call: Call<String>,
                                            response: Response<String>
                                        ) {
                                            if (response.code() == 201) {
                                                Toast.makeText(
                                                    this@ReadNoteActivity,
                                                    "추천하였습니다.",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                noteLike.text = (noteLike.text.toString()
                                                    .toInt() + 1).toString()
                                                temp = 1
                                            }
                                        }

                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                            Toast.makeText(
                                                this@ReadNoteActivity,
                                                "추천에 실패하였습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("추천 실패", t.message.toString())
                                        }

                                    })
                            }
                        }
                    }

                    noteDislikeButton.setOnClickListener{
                        this.let {
                            if(already == 1){
                                Toast.makeText(this@ReadNoteActivity,"이미 투표한 게시물입니다.", Toast.LENGTH_SHORT).show()
                            }

                            else {
                                api.noteVote(noteIdx.toString(), "up")
                                    .enqueue(object : Callback<String> {
                                        override fun onResponse(
                                            call: Call<String>,
                                            response: Response<String>
                                        ) {
                                            if (response.code() == 201) {
                                                Toast.makeText(
                                                    this@ReadNoteActivity,
                                                    "비추천하였습니다.",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                noteLike.text = (noteDislike.text.toString()
                                                    .toInt() + 1).toString()
                                                temp = 1
                                            }
                                        }

                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                            Toast.makeText(
                                                this@ReadNoteActivity,
                                                "비추천에 실패하였습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("추천 실패", t.message.toString())
                                        }

                                    })
                            }
                        }
                    }
                }

            }
            override fun onFailure(call: Call<ArrayList<NoteArticleData>>, t: Throwable) {
                Toast.makeText(this@ReadNoteActivity,"글 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                Log.d("note_read 실패", t.message.toString());
            }

        })
    }
}