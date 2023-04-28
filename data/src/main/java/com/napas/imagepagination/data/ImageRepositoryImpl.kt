package com.napas.imagepagination.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.napas.imagepagination.data.paging.ImagePagingSourceFactory
import com.napas.imagepagination.data.util.Constants.PAGE_SIZE

class ImageRepositoryImpl(
    private val createPagingSourceFactory: ImagePagingSourceFactory
) : ImageRepository {
    override fun getImages() = Pager(PagingConfig(PAGE_SIZE)) {
        createPagingSourceFactory.create()
    }.flow
}