package com.inhaproject.karaoke3.ui.community.freeboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.databinding.ActivityFreeboardBinding

class FreeBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFreeboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}