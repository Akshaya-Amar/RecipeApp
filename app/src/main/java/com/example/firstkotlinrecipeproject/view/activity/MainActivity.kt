package com.example.firstkotlinrecipeproject.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.databinding.ActivityMainBinding
import com.example.firstkotlinrecipeproject.view.adapter.RecipeAdapter
import com.example.firstkotlinrecipeproject.view.viewmodel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel: RecipeViewModel by viewModels()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.shimmerEffect.startShimmer()

        val recipeAdapter by lazy {
            RecipeAdapter { recipe ->
                val intent = Intent(this@MainActivity, RecipeInfo::class.java)
                intent.putExtra("recipe", recipe)
                startActivity(intent)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipeAdapter
        }

        viewModel.recipeData.observe(this) { data ->

            binding.shimmerEffect.stopShimmer()
            recipeAdapter.submitList(data.recipes)

            /*data.recipes?.forEach { recipe ->
                Log.i("data...", "${recipe.id}, ${recipe.title}, ${recipe.pricePerServing}")
            }*/
        }
    }
}