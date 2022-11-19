package com.inhaproject.karaoke3.ui.community.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    var checkedIndex = 0
    private var price = "1000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)

        val priceBtn = findViewById<Button>(R.id.packPriceButton)
        val priceTv = findViewById<TextView>(R.id.priceSettingTextView)

        priceBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("패키지 가격 (원)")
            val itemList = arrayOf("500","1000","1500","2000")

            builder.setSingleChoiceItems(itemList,checkedIndex) {
                    dialog, which -> checkedIndex = which

            }
            builder.setPositiveButton("확인") {dialog, which ->
                priceTv.text = itemList[checkedIndex]
            }
            builder.show()
        }

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val packList = findViewById<EditText>(R.id.priceEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()

            val intent = Intent(this, BoardActivity::class.java)

            price = priceTv.text.toString()

            if(title.isEmpty() || packList.isEmpty() || content.isEmpty()){
                Toast.makeText(this@AddArticleActivity,"빈 내용이 있습니다.",Toast.LENGTH_SHORT).show()
            }

            else {
                api.packWrite(title, content, packList, price.toString()).enqueue(object :
                    Callback<Response<Void>> {
                    override fun onResponse(
                        call: Call<Response<Void>>,
                        response: Response<Response<Void>>
                    ) {
                        if (response.isSuccessful) {
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
                        Toast.makeText(this@AddArticleActivity, "게시물 등록 완료", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(intent)
                        finish()
                        Log.d("글 등록 실패", t.message.toString());
                    }

                })
            }
        }
    }

}