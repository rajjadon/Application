package com.example.application.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.application.R
import com.example.application.common.BaseAdapter
import com.example.application.data.model.Search
import com.example.application.databinding.SearchItemsBinding

class SearchAdapter :
    BaseAdapter<Search, SearchItemsBinding>(object : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

    }, R.layout.search_items) {
    override fun bind(viewBinding: SearchItemsBinding, item: Search, position: Int) {
        viewBinding.searchedObj = item
        viewBinding.cardView.setOnClickListener {
            listener(it, item, position)
        }
    }
}