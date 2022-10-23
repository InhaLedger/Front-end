package com.inhaproject.karaoke3.ui.community

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommunityData(
    val name: String,
    val sub: String ,
    val img: Int
) : Parcelable
