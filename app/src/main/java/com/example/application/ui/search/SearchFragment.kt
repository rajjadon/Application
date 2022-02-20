package com.example.application.ui.search

import com.example.application.R
import com.example.application.common.BaseFragment
import com.example.application.databinding.FragmentSearchBinding
import com.example.application.ui.search.adapter.SearchAdapter

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchAdapter by lazy { SearchAdapter() }
    override fun setObserver() {
        TODO("Not yet implemented")
    }

    override fun setUpBindingVariables() {
        TODO("Not yet implemented")
    }

    override fun setClickListener() {
        TODO("Not yet implemented")
    }
}