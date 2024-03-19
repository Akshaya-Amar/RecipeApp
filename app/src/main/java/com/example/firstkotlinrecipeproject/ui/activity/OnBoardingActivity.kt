package com.example.firstkotlinrecipeproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firstkotlinrecipeproject.MyApp
import com.example.firstkotlinrecipeproject.databinding.ActivityOnBoardingBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.proceed.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotBlank()) {
                lifecycleScope.launch {
                    MyApp.getDataStoreContext().saveFirstTimeValues(false, name)
                    startActivity(RecipeActivity.getIntent(this@OnBoardingActivity))
                    finish()
                }

            } else {
                Snackbar.make(binding.root, "Please enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }
}