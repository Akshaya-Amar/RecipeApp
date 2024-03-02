package com.example.firstkotlinrecipeproject.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstkotlinrecipeproject.data.model.MyData
import com.example.firstkotlinrecipeproject.data.repository.MyRepository

class RecipeViewModel : ViewModel() {

    val recipe = MutableLiveData<MyData>()

    init {
        getRecipes()
    }

    private fun getRecipes() {

        MyRepository().getRecipes(object : MyRepository.MyCallBack {
            override fun onSuccess(recipes: MyData) {
                recipe.postValue(recipes)
            }

            override fun onFailure(errorMessage: String) {
                Log.i("failure...", "onFailure: $errorMessage")
            }
        })

        // why no usage???
        fun getRecipe(): LiveData<MyData> {
            return recipe
        }
    }
}