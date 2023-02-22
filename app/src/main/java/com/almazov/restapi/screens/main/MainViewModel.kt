package com.almazov.restapi.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.abstract_classes.NewsViewModel
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository): NewsViewModel() {

    val news: MutableLiveData<List<Article>> = MutableLiveData()
    var newsPage = 1

    private val countryCode = "us"

    init {
        getNews()
    }

    private fun getNews() = viewModelScope.launch {
        val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
        if (response.isSuccessful) {
            val articles = response.body()?.articles ?: emptyList()
            news.postValue(news.value?.plus(articles) ?: articles)
        }
    }

    override fun loadMore() {
        newsPage += 1
        getNews()
    }

}