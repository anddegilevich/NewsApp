package com.almazov.restapi.screens.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.Article
import com.almazov.restapi.model.NewsResponse
import com.almazov.restapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    lateinit var favouriteNewsLiveData: LiveData<List<Article>>

    init {
        getSavedArticles()
    }

    fun getSavedArticles() {
            favouriteNewsLiveData = repository.getFavouriteArticles()
        }

    fun saveFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavourite(article = article)
    }
}