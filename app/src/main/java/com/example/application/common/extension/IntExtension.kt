package com.example.application.common.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

fun <T> Int.inflateLayout(
    inflater: LayoutInflater,
    container: ViewGroup?
): T = DataBindingUtil.inflate(inflater, this, container, false)