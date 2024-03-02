package com.example.firstkotlinrecipeproject.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.view.viewmodel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val viewModel: RecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        val viewModel: RecipeViewModel by viewModels()

        viewModel.recipe.observe(this) {
            it.recipes?.forEach { recipe ->
                Log.i("data...", "${recipe.id}, ${recipe.title}")
                Snackbar.make(findViewById(R.id.constraint_layout), "$recipe.title", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}