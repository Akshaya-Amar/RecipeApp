package com.example.firstkotlinrecipeproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkotlinrecipeproject.MyApp
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.data.repository.MyRepository
import com.example.firstkotlinrecipeproject.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeViewModel @JvmOverloads constructor(

    private val repo: MyRepository = MyRepository()

) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    private val _recipeData = MutableLiveData<Response<List<Recipe>>>(Response.Loading())
    private val _recipeInfo = MutableLiveData<Response<Recipe>>()
    private val _similarRecipes = MutableLiveData<Response<List<SimilarRecipe>>>()
    val userName: LiveData<String> get() = _userName
    val recipeData: LiveData<Response<List<Recipe>>> get() = _recipeData
    val recipeInfo: LiveData<Response<Recipe>> get() = _recipeInfo
    val similarRecipes: LiveData<Response<List<SimilarRecipe>>> get() = _similarRecipes

    init {
        getRecipes()
        viewModelScope.launch {
            MyApp.getDataStoreContext().name.collectLatest { username ->
                _userName.postValue("Welcome, \n $username")
            }
        }
    }

    fun getRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = repo.getRecipes()
            _recipeData.postValue(Response.Loading())
            delay(500)
            _recipeData.postValue(resp)
        }
    }

    fun getRecipeInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = repo.getRecipeInfo(id)
            _recipeInfo.postValue(Response.Loading())
            delay(500)
            _recipeInfo.postValue(resp)
        }
    }

    fun getSimilarRecipes(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val resp = repo.getSimilarRecipes(id)
            _similarRecipes.postValue(Response.Loading())
            delay(500)
            _similarRecipes.postValue(resp)
        }
    }
}
