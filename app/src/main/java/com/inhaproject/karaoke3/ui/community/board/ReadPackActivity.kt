package com.inhaproject.karaoke3.ui.community.board

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityReadPackBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import kotlinx.android.synthetic.main.activity_song_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadPackActivity: AppCompatActivity() {

    private lateinit var binding: ActivityReadPackBinding

    private val api = RetroInterface.create();

    var data : PackArticleData? = null

    var already = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_pack)

        val packTitle = findViewById<TextView>(R.id.pack_Title)
        val packWriter = findViewById<TextView>(R.id.pack_Writer)
        val packLike = findViewById<TextView>(R.id.pack_Like_TextView)
        val packList = findViewById<TextView>(R.id.pack_Package)
        val packContent = findViewById<TextView>(R.id.pack_Content)
        val packLikeButton = findViewById<ImageButton>(R.id.pack_Like)
        val packDislikeButton = findViewById<ImageButton>(R.id.pack_Dislike)
        val packDislike = findViewById<TextView>(R.id.pack_DisLike_TextView)

        intent = intent
        val packidx =  intent.getStringExtra("게시물 번호")

        Log.d("추천 유무",intent.getStringExtra("추천 수").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("추천 수"))) {
            packLike.text = intent.getStringExtra("추천 수").toString()
        }

        else packLike.text = "0"

        Log.d("비추천 유무",intent.getStringExtra("비추천 수").toString())
        if (!TextUtils.isEmpty(intent.getStringExtra("비추천 수"))) {
            packDislike.text = intent.getStringExtra("비추천 수").toString()
        }

        else packDislike.text = "0"

        api.packRead(packidx.toString()).enqueue(object : Callback<ArrayList<PackArticleData>>{
            override fun onResponse(
                call: Call<ArrayList<PackArticleData>>,
                response: Response<ArrayList<PackArticleData>>
            ) {
                if (response.body().toString().isNotEmpty()) {
                    response.body().let {
                        packTitle.text = response.body()?.get(0)?.packtitle.toString()
                        packWriter.text = "작성자 : " + response.body()?.get(0)?.userid
                        packList.text = "패키지 구성: " + response.body()?.get(0)?.packlist.toString()
                        packContent.text = response.body()?.get(0)?.packcontent.toString()
                        already = response.body()?.get(0)?.already_vote!!
                    }
                    packLikeButton.setOnClickListener{
                        if(already == 1){
                            Toast.makeText(this@ReadPackActivity,"이미 투표한 게시물입니다.", Toast.LENGTH_SHORT).show()
                        }

                        else {
                            this.let {
                                api.packVote(packidx.toString(), "up")
                                    .enqueue(object : Callback<String> {
                                        override fun onResponse(
                                            call: Call<String>,
                                            response: Response<String>
                                        ) {
                                            if (response.code() == 201) {
                                                Toast.makeText(
                                                    this@ReadPackActivity,
                                                    "추천하였습니다.",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                packLike.text = (packLike.text.toString()
                                                    .toInt() + 1).toString()
                                            }
                                        }

                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                            Toast.makeText(
                                                this@ReadPackActivity,
                                                "추천에 실패하였습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("추천 실패", t.message.toString())
                                        }

                                    })
                            }
                        }
                    }

                    packDislikeButton.setOnClickListener {
                        if(already == 1){
                            Toast.makeText(this@ReadPackActivity,"이미 투표한 게시물입니다.", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            api.packVote(packidx.toString(), "down")
                                .enqueue(object : Callback<String> {
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        if (response.code() == 201) {
                                            Toast.makeText(
                                                this@ReadPackActivity,
                                                "비추천하였습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            packDislike.text =
                                                (packDislike.text.toString().toInt() + 1).toString()
                                        } else if (response.code() == 403 || already == 1) {
                                            Toast.makeText(
                                                this@ReadPackActivity,
                                                "이미 투표한 게시물입니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Toast.makeText(
                                            this@ReadPackActivity,
                                            "추천에 실패하였습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.d("추천 실패", t.message.toString())
                                    }

                                })
                        }
                    }
                }

            }
            override fun onFailure(call: Call<ArrayList<PackArticleData>>, t: Throwable) {
                Toast.makeText(this@ReadPackActivity,"글 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                Log.d("pack_read 실패", t.message.toString())
            }

        })
    }
}