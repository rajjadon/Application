package com.example.application.common.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

fun <T> Int.inflateLayout(
    inflater: LayoutInflater,
    container: ViewGroup?
): T = DataBindingUtil.inflate(inflater, this, container, false)

fun <T> Int.setContentView(
    activity: AppCompatActivity
): T = DataBindingUtil.setContentView(activity, this)