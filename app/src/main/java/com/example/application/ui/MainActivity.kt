package com.example.application.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.application.R
import com.example.application.common.BaseActivity
import com.example.application.common.DataLoading
import com.example.application.common.extension.showToast
import com.example.application.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var dataLoading: DataLoading

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {

            launch {
                dataLoading.isLoading.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        activityBinding.isLoading = it
                    }
            }

            launch {
                dataLoading.apiError.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        it.showToast(this@MainActivity)
                    }
            }
        }
    }
}