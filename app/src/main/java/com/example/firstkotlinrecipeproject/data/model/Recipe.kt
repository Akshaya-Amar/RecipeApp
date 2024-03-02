package com.example.firstkotlinrecipeproject.data.model

import com.google.gson.annotations.SerializedName

class Recipe(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String
)