package com.inhaproject.karaoke3.ui.community.fixboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityFixAddArticleBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import kotlinx.android.synthetic.main.activity_fix_add_article.*
import kotlinx.android.synthetic.main.item_fix_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixAddArticleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFixAddArticleBinding
    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fix_add_article)

        fix_sing_title.setText(intent.getStringExtra("노래 제목"))
        fix_singer.setText(intent.getStringExtra("가수"))
        fix_no_textview.setText(intent.getStringExtra("번호").toString())
        fixReleaseEditText.setText(intent.getStringExtra("발매일"))
        fixComposerEditText.setText(intent.getStringExtra("작곡자"))
        fixLyricistEditText.setText(intent.getStringExtra("작사자"))
        fixAlbumEditText.setText(intent.getStringExtra("앨범 제목"))
        fixAlbumImageEditText.setText(intent.getStringExtra("앨범 이미지"))

        findViewById<Button>(R.id.fixSubmitButton).setOnClickListener {
            val singTitle = findViewById<EditText>(R.id.fix_sing_title).text.toString()
            val singer = findViewById<EditText>(R.id.fix_singer).text.toString()
            val no = findViewById<EditText>(R.id.fix_no_textview).text.toString()
            val release = findViewById<EditText>(R.id.fixReleaseEditText).text.toString()
            val composer = findViewById<EditText>(R.id.fixComposerEditText).text.toString()
            val lyricist = findViewById<EditText>(R.id.fixLyricistEditText).text.toString()
            val album = findViewById<EditText>(R.id.fixAlbumEditText).text.toString()
            val imageUrl = findViewById<EditText>(R.id.fixAlbumImageEditText).text.toString()
            val title = findViewById<EditText>(R.id.fixTitleEditText).text.toString()
            val content = findViewById<EditText>(R.id.fixContentEditText).text.toString()

            val intent = Intent(this,FixBoardActivity::class.java)

            api.fixWrite(title,content,no,singTitle,"",singer,composer,lyricist,
            release,album,imageUrl).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful) {
                        Toast.makeText(
                            this@FixAddArticleActivity,
                            "게시물 등록 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                        finish()
                    }
                    if(response.code() == 400){
                        Toast.makeText(
                            this@FixAddArticleActivity,
                            "등록 실패 Code: 400",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@FixAddArticleActivity,"게시물 등록 완료",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                    Log.d("글 등록 실패", t.message.toString());
                }

            })
        }

    }
}