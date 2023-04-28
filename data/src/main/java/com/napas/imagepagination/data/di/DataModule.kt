package com.napas.imagepagination.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.napas.imagepagination.data.BuildConfig
import com.napas.imagepagination.data.paging.ImagePagingSource
import com.napas.imagepagination.data.ImageRepository
import com.napas.imagepagination.data.ImageRepositoryImpl
import com.napas.imagepagination.data.paging.ImagePagingSourceFactory
import com.napas.imagepagination.data.remote.ImageRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            }
        )
        .build()

    @Provides
    fun provideRetrofit(networkJson: Json, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    fun provideImageRemoteDataSource(retrofit: Retrofit) =
        retrofit.create(ImageRemoteDataSource::class.java)

    @Provides
    fun provideImagePagingSource(remoteDataSource: ImageRemoteDataSource) =
        ImagePagingSourceFactory {
            ImagePagingSource(remoteDataSource)
        }

    @Provides
    fun provideImageRepository(pagingSourceFactory: ImagePagingSourceFactory): ImageRepository =
        ImageRepositoryImpl(pagingSourceFactory)
}