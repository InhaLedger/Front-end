package com.inhaproject.karaoke3.ui.mypage.coin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityCoinBinding

class CoinActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_coin)

        val transferBtn : Button = findViewById(R.id.moveTransferButton)
        val depositBtn : Button = findViewById(R.id.moveDetailDepositButton)

        transferBtn.setOnClickListener {
            val intent = Intent(this,TransferActivity::class.java)
            startActivity(intent)
            finish()
        }

        depositBtn.setOnClickListener {
            val intent = Intent(this,DepositActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}