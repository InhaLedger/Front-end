package com.inhaproject.karaoke3.ui.mypage.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityAdminBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface

class AdminActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin)

        binding.moveTransferButton.setOnClickListener {
            val intent = Intent(this, SystemDepositActivity::class.java)
            startActivity(intent)
        }
    }

}