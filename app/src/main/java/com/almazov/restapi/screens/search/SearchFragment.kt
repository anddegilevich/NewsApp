package com.almazov.restapi.screens.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.almazov.restapi.R
import com.almazov.restapi.databinding.FragmentSearchBinding
import com.almazov.restapi.screens.adapters.NewsAdapter
import com.almazov.restapi.screens.adapters.NewsAdapterInterface
import com.almazov.restapi.screens.adapters.NewsAdapterViewModel
import com.almazov.restapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), NewsAdapterInterface {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    private val viewModelAdapter by viewModels<NewsAdapterViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
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
            navigationId = R.id.action_searchFragment_to_detailsFragment
        )
        var job: Job? = null
        edit_search.addTextChangedListener{ text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.makeQuery(query = it.toString())
                    }
                }
            }
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    pag_progress_bar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    pag_progress_bar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", "SearchFragment: error: $it")
                    }
                }
                is Resource.Loading -> {
                    pag_progress_bar.visibility = View.VISIBLE
                }

            }
        }
    }
}