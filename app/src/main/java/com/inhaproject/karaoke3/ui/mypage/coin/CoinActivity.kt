package com.inhaproject.karaoke3.ui.mypage.coin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityCoinBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCoinBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_coin)

        val transferBtn : Button = findViewById(R.id.moveTransferButton)
        val depositBtn : Button = findViewById(R.id.moveDetailDepositButton)

        api.myCoin().enqueue(object : Callback<BalanceData> {
            override fun onResponse(call: Call<BalanceData>, response: Response<BalanceData>) {
                if(response.code() == 200){
                    binding.holdingCoinManage.text =
                        (response.body()?.availableBalance?.plus(response.body()?.stakedBalance!!)).toString()
                    binding.avaCoin.text = response.body()?.availableBalance.toString()
                    binding.depositedCoin.text = response.body()?.stakedBalance.toString()
                }
            }

            override fun onFailure(call: Call<BalanceData>, t: Throwable) {
                Toast.makeText(
                    this@CoinActivity, "잔고 조회 실패", Toast.LENGTH_SHORT
                ).show()
                Log.d("잔고 조회 오류",t.message.toString())
            }

        })

        transferBtn.setOnClickListener {
            val intent = Intent(this,TransferActivity::class.java)
            startActivity(intent)
        }

        depositBtn.setOnClickListener {
            val intent = Intent(this,DepositActivity::class.java)
            startActivity(intent)
        }
    }

}