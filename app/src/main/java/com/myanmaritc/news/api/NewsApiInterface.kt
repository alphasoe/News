package com.myanmaritc.news.api

import com.myanmaritc.news.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiInterface {

    @Headers("X-Api-Key:e5a7abe04cb44e41843fc49f810f6591")

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): Call<News>

    @Headers("X-Api-Key:e5a7abe04cb44e41843fc49f810f6591")

    @GET("everything")
    fun searchNews(
        @Query("q") query: String
    ): Call<News>
}