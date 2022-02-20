package com.example.application.ui.search

import android.text.Editable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.application.R
import com.example.application.common.BaseFragment
import com.example.application.databinding.FragmentSearchBinding
import com.example.application.ui.search.adapter.SearchAdapter
import com.example.application.ui.search.viewModel.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import primathon.android.core.debounce.DebouncedTextWatcher

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchAdapter by lazy { SearchAdapter() }
    private val searchFragmentViewModel by activityViewModels<SearchFragmentViewModel>()

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchFragmentViewModel.searchedMovies.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
                .collect {
                    searchAdapter.submitList(it.search)
                }
        }
    }

    override fun setUpBindingVariables() {
        fragmentBinding.adapter = searchAdapter
    }

    override fun setClickListener() {

        fragmentBinding.include.searchEditText.addTextChangedListener(object :
            DebouncedTextWatcher(viewLifecycleOwner.lifecycle) {
            override fun afterTextDebounced(editable: Editable?) {
                if (editable.toString().length > 3)
                    searchFragmentViewModel.searchMovies("movie", editable.toString().trim(), 1)
            }
        })

        searchAdapter.listener = { view, item, position ->

        }
    }
}