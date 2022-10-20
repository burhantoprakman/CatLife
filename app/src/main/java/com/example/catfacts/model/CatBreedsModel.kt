package com.example.catfacts.model

data class CatBreedsModel(
    val current_page: Int,
    val data: MutableList<CatDetailModel>,
    val first_page_url: String?,
    val from: Int,
    val last_page: Int,
    val last_page_url: String?,
    val next_page_url: String?,
    val path: String?,
    val per_page: Int,
    val prev_page_url: String?,
    val to: Int,
    val total: Int
)
