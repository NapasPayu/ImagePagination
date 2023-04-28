package com.napas.imagepagination.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.napas.imagepagination.data.model.Image
import com.napas.imagepagination.data.remote.ImageRemoteDataSource
import com.napas.imagepagination.data.remote.model.ImageEntity
import com.napas.imagepagination.data.remote.model.toImage
import com.napas.imagepagination.data.util.Constants.PAGE_SIZE
import com.napas.imagepagination.data.util.Constants.TOTAL_IMAGES

class ImagePagingSource(
    private val imageRemoteDataSource: ImageRemoteDataSource
) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: 1
            val nextPageNumber = (pageNumber + 1).takeIf { pageNumber < TOTAL_IMAGES / PAGE_SIZE }
            val response = imageRemoteDataSource.getImages(pageNumber, PAGE_SIZE)
            LoadResult.Page(
                data = response.map(ImageEntity::toImage),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}