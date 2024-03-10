package com.example.firstkotlinrecipeproject.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.firstkotlinrecipeproject.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")

    //    private val NAME_KEY = stringPreferencesKey("name")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(2000)
            val preferences = dataStore.data.first()
            val isFirstLaunch = preferences[FIRST_LAUNCH_KEY] ?: true
            if (isFirstLaunch) {
                startActivity(OnBoardingActivity.getIntent(this@SplashScreen))
            } else {
//                startActivity(RecipeActivity.getIntent(this@SplashScreen, "Sample"))
                startActivity(RecipeActivity.getIntent(this@SplashScreen))
            }
        }
    }
}
