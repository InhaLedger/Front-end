package com.inhaproject.karaoke3.ui.community.rangeboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.databinding.ActivityRangeboardBinding

class RangeBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRangeboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRangeboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}