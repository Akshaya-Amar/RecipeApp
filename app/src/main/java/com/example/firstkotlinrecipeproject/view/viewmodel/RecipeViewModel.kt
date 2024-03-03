package com.example.firstkotlinrecipeproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstkotlinrecipeproject.data.model.MyData
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel @JvmOverloads constructor(
    private val repo: MyRepository = MyRepository()
) : ViewModel() {

    val recipeData = MutableLiveData<MyData>()
    val similarRecipes = MutableLiveData<Recipe>()

    init {
        getRecipes()
    }

    private fun getRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRecipes(object : MyRepository.MyCallBack {
                override fun onSuccess(recipes: MyData) {
                    recipeData.postValue(recipes)
                }

                override fun onFailure(errorMessage: String) {
                    Log.i("failure...", "onFailure: $errorMessage")
                }
            })
        }
    }
}
