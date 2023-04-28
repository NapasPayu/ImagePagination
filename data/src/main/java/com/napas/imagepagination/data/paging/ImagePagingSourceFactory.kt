package com.napas.imagepagination.data.paging

fun interface ImagePagingSourceFactory {

    fun create(): ImagePagingSource
}