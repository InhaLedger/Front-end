package com.inhaproject.karaoke3.ui.mypage.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivitySystemDepositBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SystemDepositActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySystemDepositBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemDepositBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_system_deposit)

        val chargeBtn : Button = findViewById(R.id.transferButton)
        val coin : EditText = findViewById(R.id.Input_coin)


        chargeBtn.setOnClickListener {
            api.issueCoin(coin.text.toString()).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() == 200){
                        val intent = Intent(this@SystemDepositActivity,AdminActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            this@SystemDepositActivity, "잔고 충전 완료", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@SystemDepositActivity, "잔고 충전 실패", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("잔고 충전 오류",t.message.toString())
                }

            })
        }
    }

}