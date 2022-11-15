package com.inhaproject.karaoke3.ui.community.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityAddArticleBinding
import com.inhaproject.karaoke3.retrofit.LoginResult
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddArticleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddArticleBinding

    private val api = RetroInterface.create();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val packList = findViewById<EditText>(R.id.priceEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()

            val intent = Intent(this, BoardActivity::class.java)

            api.packWrite(title,content,packList).enqueue(object :
                Callback<Response<Void>> {
                override fun onResponse(
                    call: Call<Response<Void>>,
                    response: Response<Response<Void>>
                ) {
                    if(response.isSuccessful) {
                        Toast.makeText(
                            this@AddArticleActivity,
                            "게시물 등록 완료",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<Response<Void>>, t: Throwable) {
                    Toast.makeText(this@AddArticleActivity,"게시물 등록 완료",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                    Log.d("글 등록 실패", t.message.toString());
                }

            })

        }
    }

}