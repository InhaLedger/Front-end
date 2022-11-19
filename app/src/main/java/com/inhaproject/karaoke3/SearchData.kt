package com.inhaproject.karaoke3

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(
    @SerializedName("album") val album: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("composer") val composer: String,
    @SerializedName("highNote") val highNote: String,
    @SerializedName("imageurl") val imageurl: String,
    @SerializedName("lowNote") val lowNote: String,
    @SerializedName("lyricist") val lyricist: String,
    @SerializedName("no") val no: Int,
    @SerializedName("releasedate") val releasedate: String,
    @SerializedName("singer") val singer: String,
    @SerializedName("star") val star: Int,
    @SerializedName("title") val title: String
) :Parcelable
