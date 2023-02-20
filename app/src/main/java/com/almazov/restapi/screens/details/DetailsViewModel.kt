package com.almazov.restapi.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almazov.restapi.data.api.NewsRepository
import com.almazov.restapi.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    init {
        getSavedArticles()
    }

    fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFavouriteArticles()
    }

    fun saveFavouriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavourite(article = article)
    }

}