package com.napas.imagepagination.data.remote.model

import com.napas.imagepagination.data.model.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("download_url")
    val downloadUrl: String
)

fun ImageEntity.toImage() = Image(
    id = id,
    author = author,
    width = width,
    height = height,
    imageUrl = downloadUrl
)