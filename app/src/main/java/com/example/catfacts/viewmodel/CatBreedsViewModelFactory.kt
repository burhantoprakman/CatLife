package com.example.catfacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catfacts.repository.CatBreedsRepository

@Suppress("UNCHECKED_CAST")
class CatBreedsViewModelFactory(
    private val repository: CatBreedsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatBreedsViewModel(repository) as T
    }

}