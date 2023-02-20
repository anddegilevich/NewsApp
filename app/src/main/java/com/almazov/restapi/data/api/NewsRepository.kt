package com.almazov.restapi.data.api

import androidx.room.Query
import com.almazov.restapi.data.db.ArticleDao
import com.almazov.restapi.model.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val articleDao: ArticleDao
) {
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(country = countryCode, page = pageNumber)

    suspend fun getSearchedNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)

    fun getFavouriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavourite(article: Article) = articleDao.insertArticle(article = article)
    suspend fun deleteFromFavourite(article: Article) = articleDao.deleteArticle(article = article)
}