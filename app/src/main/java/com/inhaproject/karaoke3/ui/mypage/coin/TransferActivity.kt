package com.inhaproject.karaoke3.ui.mypage.coin

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityTransferBinding

class TransferActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding

    var checkedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_transfer)

        val selectBtn : Button = findViewById(R.id.selectCoinMenuButton)

        selectBtn.setOnClickListener {
            val coinImage : ImageView = findViewById(R.id.selectCoinImage)
            val coinName : TextView = findViewById(R.id.selectCoinName)
            val coinUnit : TextView = findViewById(R.id.unitTextView)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("코인 선택")
            val itemList = arrayOf("Bitcoin","Ethereum",
                "Binance coin")

            builder.setSingleChoiceItems(itemList, checkedIndex){
                dialog, which -> checkedIndex = which
            }
            builder.setPositiveButton("확인") {dialog, which ->
                coinName.text = itemList[checkedIndex]
                if (checkedIndex == 0) {
                    coinImage.setImageResource(R.drawable.bitcoin_icon)
                    coinUnit.text = "BTC"
                }
                else if (checkedIndex == 1) {
                    coinImage.setImageResource(R.drawable.ethereum_icon)
                    coinUnit.text = "ETH"
                }
                else if (checkedIndex == 2){
                    coinImage.setImageResource(R.drawable.bnb_icon)
                    coinUnit.text = "BNB"
                }
            }
            builder.show()
        }
    }
}