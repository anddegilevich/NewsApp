package com.almazov.restapi.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.NewsResponse
import com.almazov.restapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    init {
        getNews("us")
    }

    private fun getNews(countryCode: String) = viewModelScope.launch {
        newsLiveData.postValue(Resource.Loading())
        val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
        if (response.isSuccessful) {
            response.body().let { res ->
                newsLiveData.postValue(Resource.Success(res))

            }
        } else {
            newsLiveData.postValue(Resource.Error(message = response.message()))
        }
    }

}