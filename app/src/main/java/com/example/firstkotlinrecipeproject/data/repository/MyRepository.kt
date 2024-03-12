package com.example.firstkotlinrecipeproject.data.repository

import com.example.firstkotlinrecipeproject.data.api.ApiService
import com.example.firstkotlinrecipeproject.data.model.MyData
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.util.Response

class MyRepository @JvmOverloads constructor(
    private val apiService: ApiService = ApiService.getClient()
) {
    interface MyCallBack<T> {
        fun onSuccess(data: T)
        fun onFailure(errorMessage: String)
    }
    /*interface MyCallBack {
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
    }*/

    suspend fun getRecipes(): Response<List<Recipe>> {
        try {
//            val response = apiService.getRecipes()
            val response = apiService.getRecipes("99")
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    if (data.recipes.isNullOrEmpty().not()) {
                        return Response.Success(data = data.recipes!!)
                    } else {
                        return Response.Error(message = "Empty Data")
                    }

                } ?: run {
                    return Response.Error(message = "Empty Data")
                }
            } else {
                return Response.Error(message = response.message())
            }
        } catch (exception: Exception) {
            return Response.Error(message = exception.message)
        }
    }

    suspend fun getRecipeInfo(recipeId: Int, callBack: MyCallBack<Recipe>) {
        try {
            val response = apiService.getRecipeInfo(recipeId)
            if (response.isSuccessful) {
                response.body()?.let { recipe ->
                    callBack.onSuccess(recipe)
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

    suspend fun getSimilarRecipes(recipeId: Int, callBack: MyCallBack<List<SimilarRecipe>>) {
        try {
//            val response = apiService.getSimilarRecipe()
            val response = apiService.getSimilarRecipe(recipeId)
            if (response.isSuccessful) {
                response.body()?.let { similarRecipeList ->
                    callBack.onSuccess(similarRecipeList)
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