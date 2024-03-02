package com.example.firstkotlinrecipeproject.data.api

import com.example.firstkotlinrecipeproject.data.model.MyData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*@GET("recipes/random")
    fun getRecipies(): Call<List<Recipe>>*/

    @GET("recipes/random")
    suspend fun getRecipes(
        @Query("number") number: String
    ): Response<MyData>

    companion object {

        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->

                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", "fcff114beab647c88ae6e748795150c6")
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        fun getClient(): ApiService {

// should I insert the interceptor code inside this function?

            return Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)
        }
    }
}
