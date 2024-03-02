package com.example.firstkotlinrecipeproject.data.api

import com.example.firstkotlinrecipeproject.data.model.MyData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*@GET("recipes/random")
    fun getRecipies(): Call<List<Recipe>>*/

    @GET("recipes/random")
    suspend fun getRecipes(@Query("number") number: String): Response<MyData>
}