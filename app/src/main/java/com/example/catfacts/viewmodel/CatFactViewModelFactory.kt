package com.example.catfacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catfacts.repository.CatFactRepository

@Suppress("UNCHECKED_CAST")
class CatFactViewModelFactory(
    private val repository: CatFactRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatFactViewModel(repository) as T
    }

}