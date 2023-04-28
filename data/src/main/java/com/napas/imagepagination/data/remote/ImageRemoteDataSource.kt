package com.napas.imagepagination.data.remote

import com.napas.imagepagination.data.remote.model.ImageEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageRemoteDataSource {

    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageEntity>
}