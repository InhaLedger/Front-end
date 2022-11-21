package com.inhaproject.karaoke3.ui.community.newboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityNewAddArticleBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewAddArticleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNewAddArticleBinding
    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_add_article)

        findViewById<Button>(R.id.newSubmitButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.newTitleEditText).text.toString()
            val content = findViewById<EditText>(R.id.newContentEditText).text.toString()
            val songTitle = findViewById<EditText>(R.id.new_sing_title).text.toString()
            val singer = findViewById<EditText>(R.id.new_singer).text.toString()
            val release = findViewById<EditText>(R.id.newReleaseEditText).text.toString()
            val composer = findViewById<EditText>(R.id.newComposerEditText).text.toString()
            val lyricist = findViewById<EditText>(R.id.newLyricistEditText).text.toString()
            val album = findViewById<EditText>(R.id.newAlbumEditText).text.toString()
            val imageUrl = findViewById<EditText>(R.id.newAlbumImageEditText).text.toString()
            val no = findViewById<EditText>(R.id.new_no_textview).text.toString()

            val intent = Intent(this,NewBoardActivity::class.java)

            api.newWrite(title,content,no,songTitle,"",singer,composer,lyricist,release
            ,album,imageUrl).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() == 200) {
                        Toast.makeText(
                            this@NewAddArticleActivity,
                            "게시물 등록 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                        finish()
                    }
                    if(response.code() == 400){
                        Toast.makeText(
                            this@NewAddArticleActivity,
                            "등록 실패 Code: 400",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@NewAddArticleActivity,"게시물 등록 완료",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                    Log.d("글 등록 실패", t.message.toString());
                }

            })
        }
    }

}