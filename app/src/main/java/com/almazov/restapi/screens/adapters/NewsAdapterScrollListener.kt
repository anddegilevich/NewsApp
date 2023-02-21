package com.almazov.restapi.screens.adapters
import androidx.recyclerview.widget.RecyclerView
import com.almazov.restapi.abstract_classes.NewsViewModel


class NewsAdapterScrollListener(val viewModel: NewsViewModel) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1)) {
            viewModel.loadMore()
        }
    }

}