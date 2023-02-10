package com.almazov.restapi.data.api

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {
    suspend fun getNews(countryCode: String, pageNumber: Int) = newsService.getHeadlines(
        country = countryCode, page = pageNumber)
}