package com.inhaproject.karaoke3.ui.mypage.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityAdminSendBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminSendActivity:AppCompatActivity() {
    private lateinit var binding: ActivityAdminSendBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminSendBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin_send)

        val sendBtn : Button = findViewById(R.id.adminSendButton)
        val sendUser : EditText = findViewById(R.id.InputSendUser)
        val sendCoin : EditText = findViewById(R.id.InputSendCoin)

        sendBtn.setOnClickListener {
            api.adminSendCoin(sendUser.text.toString(),
            sendCoin.text.toString()).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() == 200){
                        val intent = Intent(this@AdminSendActivity,AdminActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            this@AdminSendActivity, "송금 완료", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@AdminSendActivity, "송금 실패", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("송금 오류",t.message.toString())
                }

            })
        }
    }
}