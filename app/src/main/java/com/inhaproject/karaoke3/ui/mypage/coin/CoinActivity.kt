package com.inhaproject.karaoke3.ui.mypage.coin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchActivity
import com.inhaproject.karaoke3.SearchByNoteActivity
import com.inhaproject.karaoke3.databinding.ActivityCoinBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String.format

class CoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCoinBinding

    private val api = RetroInterface.create()

    var checkedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_coin)

        val transferBtn : Button = findViewById(R.id.moveTransferButton)
        val depositBtn : Button = findViewById(R.id.moveDetailDepositButton)
        val totalCoin : TextView = findViewById(R.id.holding_Coin_manage)
        val avaCoin : TextView = findViewById(R.id.ava_Coin)
        val depositCoin : TextView = findViewById(R.id.deposited_Coin)

        api.myCoin().enqueue(object : Callback<BalanceData> {
            override fun onResponse(call: Call<BalanceData>, response: Response<BalanceData>) {
                if(response.code() == 200){
                    totalCoin.text = (response.body()?.availableBalance?.plus(response.body()?.stakedBalance!!))
                            .toString()
                    avaCoin.text = response.body()?.availableBalance.toString()
                    depositCoin.text = response.body()?.stakedBalance.toString()
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
            val builder = AlertDialog.Builder(this)
            builder.setTitle("송금 종류 선택")
            val itemList = arrayOf("유저에게 송금","코인 지갑으로 송금")

            builder.setSingleChoiceItems(itemList, checkedIndex){
                    dialog, which -> checkedIndex = which
            }

            builder.setPositiveButton("확인") { dialog, which ->
                if (checkedIndex == 0) {
                    val intent = Intent(this, SendCoinActivity::class.java)
                    startActivity(intent)
                }
                else {
                    val intent = Intent(this, TransferActivity::class.java)
                    startActivity(intent)
                }
            }
            builder.show()
        }

        depositBtn.setOnClickListener {
            val intent = Intent(this,DepositActivity::class.java)
            startActivity(intent)
        }
    }

}