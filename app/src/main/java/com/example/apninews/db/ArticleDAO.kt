package com.example.apninews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apninews.models.Article


@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (article:Article):Long

    @Query("Select * from articles")
    fun getAllarticles():LiveData<List<Article>>

    @Delete()
    suspend fun deleteArticle(article : Article)
}