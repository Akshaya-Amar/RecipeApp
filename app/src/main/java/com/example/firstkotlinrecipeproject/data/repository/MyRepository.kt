package com.example.firstkotlinrecipeproject.data.repository

import com.example.firstkotlinrecipeproject.data.api.ApiService
import com.example.firstkotlinrecipeproject.data.model.MyData
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe

class MyRepository @JvmOverloads constructor(
    private val apiService: ApiService = ApiService.getClient()
) {
    interface MyCallBack {
        fun onSuccess(recipes: MyData)
        fun onFailure(errorMessage: String)
    }

    interface MyCallBack1 {
        fun onSuccess(recipe: Recipe)
        fun onFailure(errorMessage: String)
    }

    interface MyCallBack2 {
        fun onSuccess(recipeList: List<SimilarRecipe>)
        fun onFailure(errorMessage: String)
    }

    suspend fun getRecipes(callBack: MyCallBack) {
        try {
//            val response = apiService.getRecipes()
            val response = apiService.getRecipes("99")
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    callBack.onSuccess(data)
                } ?: run {
                    callBack.onFailure("Empty Data!")
                }
            } else {
                callBack.onFailure(response.message())
            }
        } catch (exception: Exception) {
            callBack.onFailure(exception.message.toString())
        }
    }

    suspend fun getRecipeInfo(recipeId: Int, callBack: MyCallBack1) {
        try {
            val response = apiService.getRecipeInfo(recipeId)
            if (response.isSuccessful) {
                response.body()?.let {
                    callBack.onSuccess(it)
                } ?: run {
                    callBack.onFailure("Empty Data!")
                }
            } else {
                callBack.onFailure(response.message())
            }
        } catch (exception: Exception) {
            callBack.onFailure(
                exception.message.toString()
            )
        }
    }

    suspend fun getSimilarRecipes(recipeId: Int, callBack: MyCallBack2) {
        try {
            val response = apiService.getSimilarRecipe(recipeId)
            if (response.isSuccessful) {
                response.body()?.let {
                    callBack.onSuccess(it)
                } ?: run {
                    callBack.onFailure("Empty Data!")
                }
            } else {
                callBack.onFailure(response.message())
            }
        } catch (exception: Exception) {
            callBack.onFailure(exception.message.toString())
        }
    }
}