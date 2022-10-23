package com.inhaproject.karaoke3.ui.mypage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyPageData(
    val name: String,
    val img: Int
) : Parcelable
