package com.example.catfacts.model

import java.io.Serializable

//Its extended from Serizable because adding CatDetail in bundle to pass
data class CatDetailModel(
    val breed: String?,
    val country: String?,
    val origin: String?,
    val coat: String?,
    val pattern: String?
) : Serializable
