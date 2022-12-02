package com.inhaproject.karaoke3.ui.mypage.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivitySystemDepositBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface

class SystemDepositActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySystemDepositBinding

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemDepositBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin)
    }

}