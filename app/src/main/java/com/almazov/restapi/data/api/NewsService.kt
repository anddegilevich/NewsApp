package com.almazov.restapi.data.api

import com.almazov.restapi.model.NewsResponse
import com.almazov.restapi.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("language") language: String = "en",
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ) : Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ) : Response<NewsResponse>
}