package com.inhaproject.karaoke3.ui.basket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasketData(
    val name: String,
    val singer: String ,
    val img: Int
) : Parcelable