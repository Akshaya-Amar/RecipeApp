package com.example.firstkotlinrecipeproject.data.model

import com.google.gson.annotations.SerializedName

class MyData(
    @SerializedName("recipes")
    val recipes: List<Recipe>
)

/*
data class Recipe(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?
)*/
