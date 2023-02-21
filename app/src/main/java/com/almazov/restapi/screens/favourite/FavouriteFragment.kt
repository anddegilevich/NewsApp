package com.almazov.restapi.screens.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.almazov.restapi.R
import com.almazov.restapi.databinding.FragmentFavouriteBinding
import com.almazov.restapi.screens.adapters.NewsAdapter
import com.almazov.restapi.screens.adapters.NewsAdapterInterface
import com.almazov.restapi.screens.adapters.NewsAdapterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), NewsAdapterInterface {

    private var _binding: FragmentFavouriteBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<FavouriteViewModel>()
    private val viewModelAdapter by viewModels<NewsAdapterViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = initAdapter(
            activity = activity,
            view = view,
            viewModel = viewModel,
            viewModelAdapter = viewModelAdapter,
            recyclerView = mBinding.newsRecyclerView,
            navigationId = R.id.action_favouriteFragment_to_detailsFragment
        )

        viewModel.favouriteNewsLiveData.observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }


    }


}