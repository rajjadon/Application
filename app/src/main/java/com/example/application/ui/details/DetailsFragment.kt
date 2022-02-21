package com.example.application.ui.details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.application.R
import com.example.application.common.BaseFragment
import com.example.application.databinding.FragmentDetailsBinding
import com.example.application.ui.details.viewModel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel by viewModels<DetailsViewModel>()
    private var plot = "full"

    override fun setObserver() {
        arguments?.let {
            viewModel.getMovieDetail(plot, DetailsFragmentArgs.fromBundle(it).tittle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsModel.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collect {
                fragmentBinding.details = it
            }
        }
    }

    override fun setUpBindingVariables() {
    }

    override fun setClickListener() {}
}