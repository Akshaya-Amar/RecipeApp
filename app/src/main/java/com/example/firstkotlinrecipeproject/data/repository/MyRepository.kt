package com.example.firstkotlinrecipeproject.data.repository

import com.example.firstkotlinrecipeproject.data.api.ApiService
import com.example.firstkotlinrecipeproject.data.model.MyData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MyRepository @JvmOverloads constructor(
    private val apiService: ApiService = ApiService.getClient()
) {
    interface MyCallBack {
        fun onSuccess(recipes: MyData)
        fun onFailure(errorMessage: String)
    }

    suspend fun getRecipes(callBack: MyCallBack) {
        try {
            val response = apiService.getRecipes("99")
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    callBack.onSuccess(data) } ?: run {
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