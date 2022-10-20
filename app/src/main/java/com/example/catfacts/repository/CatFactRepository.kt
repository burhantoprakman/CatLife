package com.example.catfacts.repository

import com.example.catfacts.api.RetrofitInstance

class CatFactRepository {
    suspend fun getCatFact(pageNumber: Int) = RetrofitInstance.catFactAPI.getCatFacts(pageNumber)

}