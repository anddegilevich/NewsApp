package com.almazov.restapi.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.abstract_classes.NewsViewModel
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.NewsResponse
import com.almazov.restapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository): NewsViewModel(){

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1
    var query = ""

    init {
        getSearchNews()
    }

    fun getSearchNews() =
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = repository.getSearchedNews(query = query, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    newsLiveData.postValue(Resource.Success(res))
                }
            } else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }

    fun makeQuery(query: String) {
        this.query = query
        getSearchNews()
    }

    override fun loadMore() {
        newsPage += 1
        getSearchNews()
    }

}