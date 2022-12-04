package com.inhaproject.karaoke3.ui.mypage.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityAdminBlockBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminBlockActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAdminBlockBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBlockBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin_block)

        val blockBtn : Button = findViewById(R.id.blockButton)
        val blockUser : EditText = findViewById(R.id.InputBlockUser)

        blockBtn.setOnClickListener {
            api.blockUser(blockUser.text.toString()).enqueue(object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() == 200) {
                        val intent = Intent(this@AdminBlockActivity, AdminActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            this@AdminBlockActivity, "유저 차단 완료", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@AdminBlockActivity, "유저 차단 실패", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("차단 오류",t.message.toString())
                }

            })
        }
    }

}