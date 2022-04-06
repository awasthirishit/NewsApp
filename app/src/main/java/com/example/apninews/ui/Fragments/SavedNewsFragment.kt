package com.example.apninews.ui.Fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apninews.R
import com.example.apninews.adapters.NewsAdapter
import com.example.apninews.models.Article
import com.example.apninews.ui.NewsActivity
import com.example.apninews.ui.ViewModels.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment(R.layout.fragment_saved_news), NewsAdapter.NewsClicked  {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        (activity as NewsActivity).apply {
            recyclerView = findViewById(R.id.rvSavedNews)
        }

        setupRecyclerView()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN ,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[pos]
                viewModel.deleteArticle(article)
                Snackbar.make(view,"Deleted :(",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply{
            attachToRecyclerView(recyclerView)
        }
        viewModel.getAllArticles().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })


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
            R.id.action_savedNewsFragment_to_articleViewFragment,
            bundle
        )
    }


}