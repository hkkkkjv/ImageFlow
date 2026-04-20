package ru.kpfu.itis.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kpfu.itis.data.dto.PexelsSearchResponse

interface PexelsApi {
    @GET("v1/search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): PexelsSearchResponse
}