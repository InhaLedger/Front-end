package com.inhaproject.karaoke3.recycler

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicData (
    val num : String,
    val title : String,
    val singer : String,
    val img : Int,
    val isChecked: Boolean
) : Parcelable