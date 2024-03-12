package com.example.firstkotlinrecipeproject.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.firstkotlinrecipeproject.MyApp
import com.example.firstkotlinrecipeproject.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {

            delay(1000)

            if (MyApp.getDataStoreContext().isFirstTime.first()) {
                startActivity(OnBoardingActivity.getIntent(this@SplashScreen))
                finish()
            } else {
                startActivity(RecipeActivity.getIntent(this@SplashScreen))
                finish()
            }
        }
    }
}