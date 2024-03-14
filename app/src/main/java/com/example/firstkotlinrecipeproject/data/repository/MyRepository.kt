package com.example.firstkotlinrecipeproject.data.repository

import com.example.firstkotlinrecipeproject.data.api.ApiService
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyRepository @JvmOverloads constructor(
    private val apiService: ApiService = ApiService.getClient()
) {
    /*interface MyCallBack<T> {
        fun onSuccess(data: T)
        fun onFailure(errorMessage: String)
    }*/
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

    suspend fun getRecipes(): Response<List<Recipe>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMockRecipes()
//            val response = apiService.getRecipes("99")
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    if (data.recipes.isNullOrEmpty().not()) {
                        return@withContext Response.Success(data = data.recipes!!)
                    } else {
                        return@withContext Response.Error(message = "Empty Data")
                    }
                } ?: run {
                    return@withContext Response.Error(message = "Empty Data")
                }
            } else {
                return@withContext Response.Error(message = response.message())
            }
        } catch (exception: Exception) {
            return@withContext Response.Error(message = exception.message)
        }
    }

    suspend fun getRecipeInfo(recipeId: Int): Response<Recipe> {
        try {
            val response = apiService.getMockRecipeInfo()
//            val response = apiService.getRecipeInfo(recipeId)
            if (response.isSuccessful) {
                response.body()?.let { recipe ->
                    return Response.Success(data = recipe)
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

    suspend fun getSimilarRecipes(recipeId: Int): Response<List<SimilarRecipe>> {
        try {
            val response = apiService.getMockSimilarRecipe()
//            val response = apiService.getSimilarRecipe(recipeId)
            if (response.isSuccessful) {
                response.body()?.let { similarRecipeList ->
                    return Response.Success(data = similarRecipeList)
                } ?: run {
                    return Response.Error(message = "Empty Data")
                }
            } else {
                return Response.Error(message = response.message())
            }
        } catch (exception: Exception) {
            return Response.Error(message = exception.message.toString())
        }
    }
}