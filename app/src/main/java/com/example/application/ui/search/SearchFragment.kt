package com.example.application.ui.search

import android.text.Editable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.common.BaseFragment
import com.example.application.common.DataLoading
import com.example.application.databinding.FragmentSearchBinding
import com.example.application.ui.search.adapter.SearchAdapter
import com.example.application.ui.search.viewModel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import primathon.android.core.debounce.DebouncedTextWatcher
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchAdapter by lazy { SearchAdapter() }
    private val searchFragmentViewModel by activityViewModels<SearchFragmentViewModel>()
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0
    private var pageNumber = 1
    private lateinit var layoutManager: LinearLayoutManager
    private var type = "movie"
    private var querry = ""
    private val searchFragmentArgs =
        SearchFragmentDirections.actionSearchFragmentToDetailsFragment()

    @Inject
    lateinit var dataLoading: DataLoading

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchFragmentViewModel.searchedMovies.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
                .collect {
                    searchAdapter.submitList(it)
                    fragmentBinding.adapter = searchAdapter
                }
        }
    }

    override fun setUpBindingVariables() {
        layoutManager = LinearLayoutManager(requireContext())
        fragmentBinding.rvMovie.layoutManager = layoutManager
        addPagination()
    }

    override fun setClickListener() {

        fragmentBinding.include.searchEditText.addTextChangedListener(object :
            DebouncedTextWatcher(viewLifecycleOwner.lifecycle) {
            override fun afterTextDebounced(editable: Editable?) {
                if (editable.toString().length > 3) {
                    querry = editable.toString().trim()
                    pageNumber = 1
                    searchFragmentViewModel.searchMovies(type, querry, pageNumber)
                }
            }
        })

        searchAdapter.listener = { _, item, _ ->
            findNavController().navigate(searchFragmentArgs.apply {
                tittle = item.title
            })
        }
    }

    private fun addPagination() {
        fragmentBinding.rvMovie.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        pageNumber += 1
                        searchFragmentViewModel.searchMovies(type, querry, pageNumber)
                    }
                }
            }
        })
    }
}