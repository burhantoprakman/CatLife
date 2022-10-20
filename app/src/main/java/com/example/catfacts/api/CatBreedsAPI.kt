package com.example.catfacts.api

import com.example.catfacts.model.CatBreedsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatBreedsAPI {
    @GET("/breeds")

    suspend fun getCatBreeds(
        @Query("page")
        pageNumber: Int = 1,
    ): Response<CatBreedsModel>
}