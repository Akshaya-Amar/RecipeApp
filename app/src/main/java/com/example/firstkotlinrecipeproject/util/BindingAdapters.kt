package com.example.firstkotlinrecipeproject.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:load_image")
    fun setImage(imageView: ImageView, imageUrl: String?) {
        imageUrl?.let { url ->
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }
}