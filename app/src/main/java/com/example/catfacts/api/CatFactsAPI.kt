package com.example.catfacts.api

import com.example.catfacts.model.CatFactData
import com.example.catfacts.model.CatFactModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactsAPI {
    @GET("/facts")
    suspend fun getCatFacts(
        @Query("page")
        pageNumber: Int = 1
    ): Response<CatFactData>
}