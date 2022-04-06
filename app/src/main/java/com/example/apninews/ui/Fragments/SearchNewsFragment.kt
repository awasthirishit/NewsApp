package com.example.apninews.ui.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apninews.R
import com.example.apninews.adapters.NewsAdapter
import com.example.apninews.models.Article
import com.example.apninews.ui.NewsActivity
import com.example.apninews.ui.ViewModels.NewsViewModel
import com.example.apninews.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(R.layout.fragment_search_news),NewsAdapter.NewsClicked {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var etsearch: EditText
    val TAG = "SearchNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        (activity as NewsActivity).apply {
            recyclerView = findViewById(R.id.rvSearchNews)
            progressBar = findViewById(R.id.progressBarSearch)
            etsearch = findViewById(R.id.etSearch)
        }
        setupRecyclerView()

        var job : Job? = null
        etsearch.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }



        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    showProgressBar()
                    it.message?.let{
                        Log.e(TAG,"Oops! An Error Occurred ${it}")
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(this)
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }

    }
    override fun onItemClicked(item: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", item)
        }
        findNavController().navigate(
            R.id.action_searchNewsFragment_to_articleViewFragment,
            bundle
        )
    }
}
