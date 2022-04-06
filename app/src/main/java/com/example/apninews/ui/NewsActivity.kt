package com.example.apninews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.apninews.R
import com.example.apninews.db.ArticleDatabase
import com.example.apninews.repository.NewsRepository
import com.example.apninews.ui.ViewModels.NewsViewModel
import com.example.apninews.ui.ViewModels.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {


    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val db = ArticleDatabase(this)
        val newsRepository = NewsRepository(db)
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)



        var bottomnavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val news_navhost_fragment =
            supportFragmentManager.findFragmentById(R.id.news_navhost_fragment) as NavHostFragment


        bottomnavigationView.setupWithNavController(news_navhost_fragment.findNavController())


    }

}