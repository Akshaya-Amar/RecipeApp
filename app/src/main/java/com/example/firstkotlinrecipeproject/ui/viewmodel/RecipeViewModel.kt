package com.example.firstkotlinrecipeproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkotlinrecipeproject.MyApp
import com.example.firstkotlinrecipeproject.data.model.MyData
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.data.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RecipeViewModel @JvmOverloads constructor(
    private val repo: MyRepository = MyRepository()
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    private val _recipeData = MutableLiveData<MyData>()
    private val _recipeInfo = MutableLiveData<Recipe>()
    private val _similarRecipes = MutableLiveData<List<SimilarRecipe>>()
    val userName: LiveData<String> get() = _userName
    val recipeData: LiveData<MyData> get() = _recipeData
    val recipeInfo: LiveData<Recipe> get() = _recipeInfo
    val similarRecipes: LiveData<List<SimilarRecipe>> get() = _similarRecipes

    init {
        getRecipes()
        viewModelScope.launch {
            MyApp.getDataStoreContext().name.collectLatest {
                Log.d("testrohit", "value : $it")
                _userName.postValue(it)
            }
        }
    }

    private fun getRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRecipes(object : MyRepository.MyCallBack<MyData> {
                override fun onSuccess(recipes: MyData) {
                    _recipeData.postValue(recipes)
                }

                override fun onFailure(errorMessage: String) {
                    Log.i("failure...", "onFailure: $errorMessage")
                }
            })
        }
    }

    fun getRecipeInfo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRecipeInfo(id, object : MyRepository.MyCallBack<Recipe> {
                override fun onSuccess(recipe: Recipe) {
                    _recipeInfo.postValue(recipe)
                }

                override fun onFailure(errorMessage: String) {
                    Log.i("failure...", "onFailure: $errorMessage")
                }
            })
        }
    }

    fun getSimilarRecipes(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSimilarRecipes(id, object : MyRepository.MyCallBack<List<SimilarRecipe>> {
                override fun onSuccess(recipeList: List<SimilarRecipe>) {
                    _similarRecipes.postValue(recipeList)
                }

                override fun onFailure(errorMessage: String) {
                    Log.i("failure...", "onFailure: $errorMessage")
                }
            })
        }
    }
}
