package com.inhaproject.karaoke3.ui.community.fixboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ActivityReadFixBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide

class ReadFixActivity: AppCompatActivity() {
    private lateinit var binding: ActivityReadFixBinding

    private val api = RetroInterface.create()

    var data : FixArticleData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_fix)

        val title = findViewById<TextView>(R.id.fixReadTitle)
        val writer = findViewById<TextView>(R.id.fixReadWriter)
        val like = findViewById<TextView>(R.id.fix_Like_TextView)
        val content = findViewById<TextView>(R.id.fix_Content)
        val likeButton = findViewById<ImageButton>(R.id.fix_Like_Button)
        val image = findViewById<ImageView>(R.id.fixReadImageView)
        val albumName = findViewById<TextView>(R.id.fixReadAlbumName)
        val singTitle = findViewById<TextView>(R.id.fixReadSingTitle)
        val singer = findViewById<TextView>(R.id.fixReadSinger)
        val comAndLyric = findViewById<TextView>(R.id.fixReadComposerAndLyricist)
        val releaseDate = findViewById<TextView>(R.id.fixReadReleaseDate)
        val no = findViewById<TextView>(R.id.fixReadNo)

        val idx = intent.getStringExtra("게시물 번호")

        api.fixRead(idx.toString()).enqueue(object : Callback<ArrayList<FixArticleData>>{
            override fun onResponse(
                call: Call<ArrayList<FixArticleData>>,
                response: Response<ArrayList<FixArticleData>>
            ) {
                if(response.code() == 200){
                    title.text = response.body()?.get(0)?.fix_boardtitle.toString()
                    writer.text = "작성자 : " + response.body()?.get(0)?.fix_writer.toString()
                    like.text = response.body()?.get(0)?.vote.toString()
                    content.text = response.body()?.get(0)?.fix_boardcontent.toString()
                    Glide.with(this@ReadFixActivity).load(
                        response.body()?.get(0)?.fix_imageurl.toString()
                    ).into(image)
                    albumName.text = response.body()?.get(0)?.fix_album.toString()
                    singTitle.text = response.body()?.get(0)?.fix_title.toString()
                    singer.text = response.body()?.get(0)?.fix_singer.toString()
                    comAndLyric.text = "작사 / 작곡 : "+response.body()?.get(0)?.fix_composer.toString() +" / " +
                            response.body()?.get(0)?.fix_lyricist.toString()
                    releaseDate.text = "발매일 : " + response.body()?.get(0)?.fix_releasedate.toString().substring(0 until 10)
                    no.text = "노래방 번호 : "+ response.body()?.get(0)?.fix_no.toString()

                    val beforeSingTitle = response.body()?.get(0)?.fix_title.toString()
                    val beforeSinger = response.body()?.get(0)?.fix_singer.toString()
                    val beforeComposer = response.body()?.get(0)?.fix_composer.toString()
                    val beforeLyricist = response.body()?.get(0)?.fix_lyricist.toString()
                    val beforeRelease = response.body()?.get(0)?.fix_releasedate.toString()
                    val beforeImage = response.body()?.get(0)?.fix_imageurl.toString()
                    val beforeAlbum = response.body()?.get(0)?.fix_album.toString()
                    val beforeNo = response.body()?.get(0)?.fix_no.toString()

                    api.searchSong(beforeNo,"","","","","","","")
                        .enqueue(object : Callback<ArrayList<SearchData>>{
                            override fun onResponse(
                                call: Call<ArrayList<SearchData>>,
                                response: Response<ArrayList<SearchData>>
                            ) {
                                if(response.code() == 200){

                                    if (beforeSingTitle != response.body()?.get(0)?.title.toString())
                                        singTitle.setTextColor(ContextCompat
                                            .getColor(this@ReadFixActivity, R.color.colorBlue))
                                    if (beforeSinger != response.body()?.get(0)?.singer.toString())
                                        singer.setTextColor(ContextCompat
                                            .getColor(this@ReadFixActivity, R.color.colorBlue))
                                    if (beforeComposer != response.body()?.get(0)?.composer.toString()
                                        || beforeLyricist != response.body()?.get(0)?.lyricist.toString())
                                        comAndLyric.setTextColor(ContextCompat
                                            .getColor(this@ReadFixActivity, R.color.colorBlue))
                                    if (beforeRelease != response.body()?.get(0)?.releasedate.toString())
                                        releaseDate.setTextColor(ContextCompat
                                            .getColor(this@ReadFixActivity, R.color.colorBlue))
                                    if (beforeImage != response.body()?.get(0)?.imageurl.toString())
                                        image.setBackgroundColor(Color.BLUE)
                                    if (beforeAlbum != response.body()?.get(0)?.album.toString())
                                        albumName.setTextColor(ContextCompat
                                            .getColor(this@ReadFixActivity, R.color.colorBlue))
                                }
                            }

                            override fun onFailure(
                                call: Call<ArrayList<SearchData>>,
                                t: Throwable
                            ) {
                                Toast.makeText(this@ReadFixActivity,"검색 실패", Toast.LENGTH_SHORT).show()
                                Log.d("검색 실패", t.message.toString())
                            }

                        })
                }
            }

            override fun onFailure(call: Call<ArrayList<FixArticleData>>, t: Throwable) {
                Toast.makeText(this@ReadFixActivity,"게시물 로드 실패", Toast.LENGTH_SHORT).show()
                Log.d("fix 게시물 로딩 실패", t.message.toString())
            }

        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}