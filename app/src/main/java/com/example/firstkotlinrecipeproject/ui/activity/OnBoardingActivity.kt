package com.example.firstkotlinrecipeproject.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstkotlinrecipeproject.databinding.ActivityOnBoardingBinding
import com.google.android.material.snackbar.Snackbar

class OnBoardingActivity : AppCompatActivity() {

//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//    private val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.proceed.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotBlank()) {
                /*lifecycleScope.launch {
                    dataStore.edit {
                        it[FIRST_LAUNCH_KEY] = false
                    }
                }*/
                startSampleActivity()
            } else {
                Snackbar.make(binding.root, "Please enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun startSampleActivity() {
        val intent = Intent(this@OnBoardingActivity, RecipeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }
}