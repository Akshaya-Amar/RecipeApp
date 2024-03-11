package com.example.firstkotlinrecipeproject.data.api

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.firstkotlinrecipeproject.MyApp
import com.example.firstkotlinrecipeproject.data.model.MyData
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import kotlinx.coroutines.sync.Mutex
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random")
    suspend fun getRecipes(
        @Query("number") number: String
    ): Response<MyData>

    @GET("recipes/random")
    suspend fun getRecipes(): Response<MyData>

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") recipeId: Int
    ): Response<Recipe>

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipe(@Path("id") recipeId: Int): Response<List<SimilarRecipe>>

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
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(ChuckerInterceptor.Builder(MyApp.getContext().get()!!).build())
            .build()

        private val client: ApiService = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

        fun getClient(): ApiService {
            synchronized(Mutex()) {
                return client
            }
        }
    }
}
