package com.example.firstkotlinrecipeproject.data.model

import com.google.gson.annotations.SerializedName

data class MyData(
    @SerializedName("recipes")
    val recipes: List<Recipe>? = null,
)

data class Recipe(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("summary")
    val summary: String?,

    @SerializedName("instructions")
    val instructions: String?,

    @SerializedName("image")
    val imageUrl: String?,

    @SerializedName("servings")
    val servings: Int?,

    @SerializedName("readyInMinutes")
    val duration: Int?,

    @SerializedName("pricePerServing")
    val pricePerServing: Double?
)

data class SimilarRecipe(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("servings")
    val servings: Int?,

    @SerializedName("readyInMinutes")
    val duration: Int?
)

