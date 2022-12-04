package com.inhaproject.karaoke3.ui.community.newboard

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityReadNewBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadNewActivity: AppCompatActivity() {

    private lateinit var binding: ActivityReadNewBinding

    private val api = RetroInterface.create()

    var data : NewArticleData? = null

    var already = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_new)

        val newTitle = findViewById<TextView>(R.id.newReadTitle)
        val newWriter = findViewById<TextView>(R.id.new_Writer)
        val newLike = findViewById<TextView>(R.id.new_Like_TextView)
        val newDislike = findViewById<TextView>(R.id.new_DisLike_TextView)
        val newContent = findViewById<TextView>(R.id.new_Content)
        val newLikeButton = findViewById<ImageButton>(R.id.new_Like_Button)
        val newDislikeButton = findViewById<ImageButton>(R.id.new_Dislike_Button)
        val image = findViewById<ImageView>(R.id.newReadImageView)
        val albumName = findViewById<TextView>(R.id.newReadAlbumName)
        val newSingTitle = findViewById<TextView>(R.id.newReadSingTitle)
        val newSinger = findViewById<TextView>(R.id.newReadSinger)
        val comAndLyric = findViewById<TextView>(R.id.newReadComposerAndLyricist)
        val releaseDate = findViewById<TextView>(R.id.newReadReleaseDate)
        val newNo = findViewById<TextView>(R.id.newReadNo)

        val newIdx = intent.getStringExtra("게시물 번호")

        Log.d("추천 유무",intent.getStringExtra("추천 수").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("추천 수"))) {
            newLike.text = intent.getStringExtra("추천 수").toString()
        }

        else newLike.text = "0"

        Log.d("비추천 유무",intent.getStringExtra("비추천 수").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("비추천 수"))) {
            newDislike.text = intent.getStringExtra("비추천 수").toString()
        }

        else newDislike.text = "0"

        api.newRead(newIdx.toString()).enqueue(object : Callback<ArrayList<NewArticleData>>{
            override fun onResponse(
                call: Call<ArrayList<NewArticleData>>,
                response: Response<ArrayList<NewArticleData>>
            ) {
                if(response.code() == 200){
                    newTitle.text = response.body()?.get(0)?.new_boardtitle.toString()
                    newWriter.text = "작성자 : "+response.body()?.get(0)?.userid.toString()
                    newLike.text = response.body()?.get(0)?.upvote.toString()
                    newContent.text = response.body()?.get(0)?.new_boardcontent.toString()
                    Glide.with(this@ReadNewActivity)
                        .load(response.body()?.get(0)?.new_imageurl.toString()).into(image)
                    albumName.text = response.body()?.get(0)?.new_album.toString()
                    newSingTitle.text = response.body()?.get(0)?.new_title.toString()
                    newSinger.text = response.body()?.get(0)?.new_singer.toString()
                    comAndLyric.text = "작사 / 작곡 : "+response.body()?.get(0)?.new_composer.toString() +" / " +
                    response.body()?.get(0)?.new_lyricist.toString()
                    releaseDate.text = "발매일 : "+response.body()?.get(0)?.new_releasedate.toString().substring(0 until 10)
                    newNo.text ="노래방 번호 : " + response.body()?.get(0)?.new_no.toString()
                    already = response.body()?.get(0)?.already_vote!!

                }
                newLikeButton.setOnClickListener {
                    if(already == 1){
                        Toast.makeText(this@ReadNewActivity,"이미 투표한 게시물입니다.", Toast.LENGTH_SHORT).show()
                    }


                    else {
                        api.newVote(newIdx.toString(), "up").enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                if (response.code() == 201) {
                                    Toast.makeText(
                                        this@ReadNewActivity,
                                        "추천하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    newLike.text = (newLike.text.toString().toInt() + 1).toString()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Toast.makeText(
                                    this@ReadNewActivity,
                                    "추천에 실패하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("추천 실패", t.message.toString())
                            }

                        })
                    }
                }

                newDislikeButton.setOnClickListener {
                    if (already == 1) {
                        Toast.makeText(this@ReadNewActivity, "이미 투표한 게시물입니다.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        api.newVote(newIdx.toString(), "down").enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                if (response.code() == 201) {
                                    Toast.makeText(
                                        this@ReadNewActivity,
                                        "추천하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    newLike.text = (newLike.text.toString().toInt() + 1).toString()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Toast.makeText(
                                    this@ReadNewActivity,
                                    "추천에 실패하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("추천 실패", t.message.toString())
                            }

                        })
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<NewArticleData>>, t: Throwable) {
                Toast.makeText(this@ReadNewActivity,"글 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                Log.d("new_read 실패", t.message.toString())
            }

        })
    }
}