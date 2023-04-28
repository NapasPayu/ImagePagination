package com.napas.imagepagination.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val imageUrl: String
) : Parcelable
