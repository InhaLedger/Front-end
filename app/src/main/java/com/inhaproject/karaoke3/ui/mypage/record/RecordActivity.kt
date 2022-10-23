package com.inhaproject.karaoke3.ui.mypage.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityRecordBinding


class RecordActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecordBinding
    private var state = State.BEFORE_RECORDING

    private val recordButton: RecordButton by lazy{
        findViewById(R.id.recordButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    fun initViews() {
        recordButton.updateIconWithState(state)
    }

}