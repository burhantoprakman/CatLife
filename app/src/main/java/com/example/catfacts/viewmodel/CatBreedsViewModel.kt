package com.example.catfacts.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.catfacts.model.CatBreedsModel
import com.example.catfacts.model.MetricsModel
import com.example.catfacts.repository.CatBreedsRepository
import com.example.catfacts.resources.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class CatBreedsViewModel(val catBreedsRepository: CatBreedsRepository) : ViewModel() {
    val catBreedsList: MutableLiveData<Resources<CatBreedsModel>> = MutableLiveData()

    //Pagination number - default is 1
    var catBreedsPageNumber = 1

    var catBreedsModelResponse: CatBreedsModel? = null
    var averageResponseTime: MutableLiveData<Long> = MutableLiveData()

    //Average time for response
    var averageTime: Long = 0L

    init {
        getCatBreeds()
    }

    fun getCatBreeds() = viewModelScope.launch {
        catBreedsList.postValue(Resources.Loading())
        val response = catBreedsRepository.getCatBreeds(catBreedsPageNumber)
        catBreedsList.postValue(handleCatBreeds(response))
        averageResponseTime.postValue(averageTime)
    }

    private fun handleCatBreeds(response: Response<CatBreedsModel>)
            : Resources<CatBreedsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // Increase page number for next request
                catBreedsPageNumber++

                //If its our first time to got data
                if (catBreedsModelResponse == null) {
                    catBreedsModelResponse = resultResponse
                } else {
                    //Add new facts list from other page to old list
                    val oldCatBreedsList = catBreedsModelResponse?.data
                    val newCatBreedsList = resultResponse.data
                    oldCatBreedsList?.addAll(newCatBreedsList)
                }
                //Get response time
                averageTime =
                    response.raw().receivedResponseAtMillis - response.raw().sentRequestAtMillis


                return Resources.Success(catBreedsModelResponse ?: resultResponse)
            }
        }
        return Resources.Error(response.message())
    }

}