package com.example.e_commerce.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("load_backdrop")
fun loadBackdrop(image: ImageView, url: String?) {
    if (url != null) {
        val context = image.context
        Glide.with(context).load("https://image.tmdb.org/t/p/original/$url").into(image)
    }
}

@BindingAdapter("load_image")
fun loadPoster(image: ImageView, url: String?) {
    if (url != null) {
        val context = image.context
        Glide.with(context).load(url).into(image)
    }
}