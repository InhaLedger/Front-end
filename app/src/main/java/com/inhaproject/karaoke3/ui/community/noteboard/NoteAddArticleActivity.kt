package com.inhaproject.karaoke3.ui.community.noteboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ActivityNoteAddArticleBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import kotlinx.android.synthetic.main.activity_note_add_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteAddArticleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNoteAddArticleBinding
    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_article)

        val singTitle = intent.getStringExtra("노래 제목")
        val singer = intent.getStringExtra("가수")
        val no = intent.getStringExtra("번호").toString()

        note_sing_title.text = singTitle
        note_singer.text = singer
        noteContentEditText.setSelection(0)

        findViewById<Button>(R.id.noteSubmitButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.noteTitleEditText).text.toString()
            val content = findViewById<EditText>(R.id.noteContentEditText).text.toString()
            val highNote = findViewById<EditText>(R.id.highNoteEditText).text.toString()
            val lowNote = findViewById<EditText>(R.id.lowNoteEditText).text.toString()

            val inten = Intent(this,NoteBoardActivity::class.java)

            api.noteWrite(no,title,content,highNote,lowNote).enqueue(object :
                Callback<String> {

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if(response.isSuccessful) {
                        Toast.makeText(
                            this@NoteAddArticleActivity,
                            "게시물 등록 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(inten)
                        finish()
                    }
                    if(response.code() == 400){
                        Toast.makeText(
                            this@NoteAddArticleActivity,
                            "등록 실패 Code: 400",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@NoteAddArticleActivity,"게시물 등록 완료",Toast.LENGTH_SHORT).show()
                    startActivity(inten)
                    finish()
                    Log.d("글 등록 실패", t.message.toString());
                }

            })
        }
    }

}