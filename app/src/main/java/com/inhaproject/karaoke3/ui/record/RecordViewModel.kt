package com.inhaproject.karaokeapp.ui.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "녹음 페이지"
    }
    val text: LiveData<String> = _text
}