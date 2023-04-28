package com.napas.imagepagination.data

import androidx.paging.PagingData
import com.napas.imagepagination.data.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getImages(): Flow<PagingData<Image>>
}