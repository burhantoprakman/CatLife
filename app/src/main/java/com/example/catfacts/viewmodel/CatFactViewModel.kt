package com.example.catfacts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.model.CatFactData
import com.example.catfacts.model.CatFactModel
import com.example.catfacts.repository.CatFactRepository
import com.example.catfacts.resources.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class CatFactViewModel(private val repository: CatFactRepository) : ViewModel() {
    val catFactsList: MutableLiveData<Resources<MutableList<CatFactModel>>> = MutableLiveData()

    //Pagination number - default is 1
    var catFactPageNumber = 1
    var catFactModelResponse: MutableList<CatFactModel>? = null
    var averageResponseTime: MutableLiveData<Long> = MutableLiveData()

    //Average time for response
    var averageTime: Long = 0L

    init {
        getCatFacts()
    }

    fun getCatFacts() = viewModelScope.launch {
        catFactsList.postValue(Resources.Loading())
        val response = repository.getCatFact(catFactPageNumber)
        catFactsList.postValue(handleCatFacts(response))
        averageResponseTime.postValue(averageTime)
    }

    //
    private fun handleCatFacts(response: Response<CatFactData>)
            : Resources<MutableList<CatFactModel>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // Increase page number for next request
                catFactPageNumber++

                //If its our first time to got data
                if (catFactModelResponse == null) {
                    catFactModelResponse = resultResponse.data
                } else {
                    //Add new facts list from other page to old list
                    val oldCatFactList = catFactModelResponse
                    val newCatFactList = resultResponse
                    oldCatFactList?.addAll(newCatFactList.data)
                }
                //Get response time
                averageTime =
                    response.raw().receivedResponseAtMillis - response.raw().sentRequestAtMillis


                return Resources.Success(catFactModelResponse ?: resultResponse.data)
            }
        }
        return Resources.Error(response.message())
    }
}