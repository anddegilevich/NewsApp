package com.almazov.restapi.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.abstract_classes.NewsViewModel
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository): NewsViewModel(){

    val searchedNews: MutableLiveData<List<Article>> = MutableLiveData()
    var newsPage = 1
    var query = ""

    private fun getSearchedNews() =
        viewModelScope.launch {
            val response = repository.getSearchedNews(query = query, pageNumber = newsPage)
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                searchedNews.postValue(articles)
            }
        }

    fun makeQuery(query: String) {
        this.query = query
        newsPage = 1
        getSearchedNews()
    }

    override fun loadMore() {
        newsPage += 1
        viewModelScope.launch {
            val response = repository.getSearchedNews(query = query, pageNumber = newsPage)
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                searchedNews.postValue(searchedNews.value?.plus(articles) ?: articles)
            }
        }
    }

}