package com.example.firstkotlinrecipeproject.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
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

    @JvmStatic
    @BindingAdapter("app:load_image_using_id")
    fun setImageUsingId(imageView: ImageView, id: Int?) {
        id?.let {
            Glide.with(imageView.context)
                .load("https://spoonacular.com/recipeImages/$id-556x370.jpg")
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:set_text")
    fun setText(textView: TextView, description: String?) {
        val instruction =
            description?.let {
                HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            } ?: ""
        textView.text = instruction
    }
}