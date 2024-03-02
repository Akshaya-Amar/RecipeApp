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

class MyRepository {

    private val apiService: ApiService

    interface MyCallBack {
        fun onSuccess(recipes: MyData)
        fun onFailure(errorMessage: String)
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->

                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", "enter_api_key_here")
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val builder: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService =
            builder.create(ApiService::class.java) // should i remove it from singleton or init?
    }

    fun getRecipes(callBack: MyCallBack) {
        GlobalScope.launch {
            val response = apiService.getRecipes("99")
            try {
                if (response.isSuccessful) {
                    response.body()?.let { callBack.onSuccess(it) }
                } else {
                    callBack.onFailure("Is not successful: $response.message(), ${response.code()}")
                }
            } catch (exception: Exception) {
                callBack.onFailure("exception $exception.message.toString()")
            }
        }
    }
}