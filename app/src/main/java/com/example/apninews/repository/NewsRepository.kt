package com.example.apninews.repository

import com.example.apninews.api.RetrofitInstance
import com.example.apninews.db.ArticleDatabase
import com.example.apninews.models.Article
import com.example.apninews.models.NewsResponse
import com.example.apninews.util.Constants.Companion.API_KEY
import retrofit2.Retrofit

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.search(searchQuery, pageNumber)

    suspend fun insert(article: Article) = db.getArticleDAO().insert(article)

    fun getAllArticles() = db.getArticleDAO().getAllarticles()

    suspend fun delete(article: Article) = db.getArticleDAO().deleteArticle(article)
}