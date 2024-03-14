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
import retrofit2.http.Url

interface ApiService {

    @GET("recipes/random")
    suspend fun getRecipes(
        @Query("number") number: String
    ): Response<MyData>

    @GET("recipes/random")
    suspend fun getRecipes(): Response<MyData>

    @GET("")
    suspend fun getMockRecipes(
        @Url url: String = "https://run.mocky.io/v3/6856144b-dc10-403c-9636-2c4c58cab789"
    ): Response<MyData>

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") recipeId: Int
    ): Response<Recipe>

    @GET("")
    suspend fun getMockRecipeInfo(
        @Url url: String = "https://run.mocky.io/v3/f1ac76ce-ad44-4b53-9002-68e6f7f77730"
    ): Response<Recipe>

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipe(@Path("id") recipeId: Int): Response<List<SimilarRecipe>>

    @GET("")
    suspend fun getMockSimilarRecipe(
        @Url url: String = "https://run.mocky.io/v3/53b66702-1a88-4119-8715-76f41e901b64"
    ): Response<List<SimilarRecipe>>

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
