package com.almazov.restapi.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.almazov.restapi.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllArticles() : LiveData<List<Article>>

    @Query("SELECT EXISTS(SELECT * FROM articles WHERE url = :url)")
    suspend fun checkFavouriteUrl(url: String) : Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
}