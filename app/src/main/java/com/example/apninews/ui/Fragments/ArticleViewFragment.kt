package com.example.apninews.ui.Fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.apninews.R
import com.example.apninews.ui.NewsActivity
import com.example.apninews.ui.ViewModels.NewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class ArticleViewFragment : Fragment(R.layout.fragment_article_view) {
    lateinit var viewModel: NewsViewModel
    lateinit var webview : WebView
    lateinit var fab : FloatingActionButton
    val args : ArticleViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel


        val article = args.article
        webview = (activity as NewsActivity).findViewById(R.id.webview)
        webview.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
        fab = (activity as NewsActivity).findViewById(R.id.fabButton)
        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }

    }
}