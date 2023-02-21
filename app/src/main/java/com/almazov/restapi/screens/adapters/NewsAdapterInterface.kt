package com.almazov.restapi.screens.adapters

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.almazov.restapi.abstract_classes.NewsViewModel

interface NewsAdapterInterface {

    fun initAdapter(
        activity: FragmentActivity?,
        view: View,
        viewModel: NewsViewModel,
        viewModelAdapter: NewsAdapterViewModel,
        recyclerView: RecyclerView,
        navigationId: Int,
    ): NewsAdapter {
        val newsAdapter = NewsAdapter(viewModelAdapter)
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(navigationId, bundle)
        }

        recyclerView.addOnScrollListener(NewsAdapterScrollListener(viewModel = viewModel))

        return newsAdapter

    }

}