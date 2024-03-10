package com.example.firstkotlinrecipeproject.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstkotlinrecipeproject.R

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, RecipeActivity::class.java)
        }

        /*fun getIntent(context: Context, userId: String): Intent {
            return Intent(context, RecipeActivity::class.java).also {
                it.extras?.putString("userId", userId)
            }
        }*/
    }
}