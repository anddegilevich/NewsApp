package com.almazov.restapi.screens.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.almazov.restapi.R
import com.almazov.restapi.databinding.FragmentMainBinding
import com.almazov.restapi.screens.adapters.NewsAdapter
import com.almazov.restapi.screens.adapters.NewsAdapterInterface
import com.almazov.restapi.screens.adapters.NewsAdapterViewModel
import com.almazov.restapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), NewsAdapterInterface {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    private val viewModelAdapter by viewModels<NewsAdapterViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
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
            navigationId = R.id.action_mainFragment_to_detailsFragment
        )

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    pag_progress_bar.visibility = View.INVISIBLE
                    response.data?.let {
                        viewModelAdapter.news.postValue(viewModelAdapter.news.value?.plus(it.articles)) //TODO
                        newsAdapter.differ.submitList(viewModelAdapter.news.value)
                    }
                }
                is Resource.Error -> {
                    pag_progress_bar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", "MainFragment: error: $it")
                    }
                }
                is Resource.Loading -> {
                    pag_progress_bar.visibility = View.VISIBLE
                }

            }
        }
    }

}