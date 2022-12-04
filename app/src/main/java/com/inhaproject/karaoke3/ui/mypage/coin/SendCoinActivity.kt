package com.inhaproject.karaoke3.ui.mypage.coin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivitySendCoinBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.admin.AdminActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendCoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySendCoinBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendCoinBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_send_coin)

        val sendBtn : Button = findViewById(R.id.sendButton)
        val searchBtn : Button = findViewById(R.id.idSearchButton)
        val sendUser : EditText = findViewById(R.id.InputSendUser)
        val sendCoin : EditText = findViewById(R.id.InputSendCoin)
        val searchId : EditText = findViewById(R.id.InputId)
        val result : TextView = findViewById(R.id.idSearchResult)

        sendBtn.setOnClickListener {
            api.sendCoin(sendUser.text.toString(),
                sendCoin.text.toString()).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() == 201){
                        val intent = Intent(this@SendCoinActivity, AdminActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            this@SendCoinActivity, "송금 완료", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@SendCoinActivity, "송금 실패", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("송금 오류",t.message.toString())
                }

            })
        }

        searchBtn.setOnClickListener {
            api.findUser(searchId.text.toString()).enqueue(object : Callback<ArrayList<FindUserData>>{
                override fun onResponse(
                    call: Call<ArrayList<FindUserData>>,
                    response: Response<ArrayList<FindUserData>>
                ) {
                    if(response.code() == 200){
                        result.text =
                            searchId.text.toString() + "의 userIdx는 " + response.body()?.get(0)?.useridx.toString()+" 입니다."
                    }
                }

                override fun onFailure(call: Call<ArrayList<FindUserData>>, t: Throwable) {
                    Toast.makeText(
                        this@SendCoinActivity, "검색 실패", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("findUser 오류",t.message.toString())
                }

            })
        }
    }

}