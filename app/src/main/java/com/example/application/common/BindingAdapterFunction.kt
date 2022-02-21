package com.example.application.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.application.R

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        if (!it.equals("N/A", true))
            Glide.with(imageView.context).load(it).into(imageView)
        else
            Glide.with(imageView.context).load(R.drawable.ic_baseline_broken).into(imageView)
    }
}