package com.example.catfacts.repository

import com.example.catfacts.api.RetrofitInstance

class CatBreedsRepository {
    suspend fun getCatBreeds(pageNumber: Int) =
        RetrofitInstance.catBreedsAPI.getCatBreeds(pageNumber)
}