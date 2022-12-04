package com.inhaproject.karaoke3.ui.mypage.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityAdminBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.coin.BalanceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin)

        val depositBtn : Button = findViewById(R.id.moveSystemTransferButton)
        val finalizeBtn : Button = findViewById(R.id.finalizeButton)
        val sendBtn : Button = findViewById(R.id.moveSendButton)
        val blockBtn : Button = findViewById(R.id.moveBlockButton)
        val proposalBtn : Button = findViewById(R.id.moveAdminProposalButton)

        depositBtn.setOnClickListener {
            val intent = Intent(this, SystemDepositActivity::class.java)
            startActivity(intent)
        }
        sendBtn.setOnClickListener {
            val intent = Intent(this,AdminSendActivity::class.java)
            startActivity(intent)
        }
        blockBtn.setOnClickListener {
            val intent = Intent(this,AdminBlockActivity::class.java)
            startActivity(intent)
        }
        proposalBtn.setOnClickListener {
            val intent = Intent(this,AdminProposalActivity::class.java)
            startActivity(intent)
        }

        api.adminMyCoin().enqueue(object : Callback<BalanceData>{
            override fun onResponse(call: Call<BalanceData>, response: Response<BalanceData>) {
                if(response.code() == 200){
                    binding.systemCoinmanage.text = response.body()?.availableBalance.toString()
                }
            }

            override fun onFailure(call: Call<BalanceData>, t: Throwable) {
                Toast.makeText(
                    this@AdminActivity, "잔고 조회 실패", Toast.LENGTH_SHORT
                ).show()
                Log.d("잔고 조회 오류",t.message.toString())
            }

        })
    }

}