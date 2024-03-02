package com.example.firstkotlinrecipeproject.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.view.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: RecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        viewModel.recipe.observe(this, Observer {
            val recipeList = it.recipes
            for (recipe in recipeList){
                Log.i("data...", "${recipe.id}, ${recipe.title}")
            }
        })
        Log.i("simple name...", "onCreate: .." + viewModel.javaClass.simpleName)
    }
}