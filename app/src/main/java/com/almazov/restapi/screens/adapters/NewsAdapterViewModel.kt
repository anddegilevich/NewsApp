package com.almazov.restapi.screens.adapters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsAdapterViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    var news: MutableLiveData<List<Article>> = MutableLiveData()

    fun saveFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavourite(article = article)
    }
    fun deleteFromFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFromFavourite(article = article)
    }

    suspend fun checkFavouriteUrl(url: String) =
        repository.checkFavouriteUrl(url = url)
}