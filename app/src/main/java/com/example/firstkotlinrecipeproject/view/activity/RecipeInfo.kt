package com.example.firstkotlinrecipeproject.view.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.databinding.ActivityRecipeInfoBinding
import com.example.firstkotlinrecipeproject.view.viewmodel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class RecipeInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityRecipeInfoBinding>(
            this,
            R.layout.activity_recipe_info
        )

        val recipe = intent.getSerializableExtra("recipe") as Recipe
        binding.recipe = recipe

//        val viewModel: RecipeViewModel by viewModels()

        var isSummaryArrowUp = false

        binding.summaryArrow.setOnClickListener {
            if (!isSummaryArrowUp) {
                val drawable = ContextCompat.getDrawable(this, R.drawable.outline_arrow_drop_up)
                binding.summaryArrow.setImageDrawable(drawable)
                binding.summaryDescription.visibility = View.VISIBLE
                isSummaryArrowUp = true
            } else {
                val drawable = ContextCompat.getDrawable(this, R.drawable.outline_arrow_drop_down)
                binding.summaryArrow.setImageDrawable(drawable)
                binding.summaryDescription.visibility = View.GONE
                isSummaryArrowUp = false
            }
        }

        var isInstructionArrowUp = false

        binding.instructionArrow.setOnClickListener {
            if(!isInstructionArrowUp){
                val drawable = ContextCompat.getDrawable(this, R.drawable.outline_arrow_drop_up)
                binding.instructionArrow.setImageDrawable(drawable)
                binding.instructionDescription.visibility = View.VISIBLE
                isInstructionArrowUp = true
            } else {
                val drawable = ContextCompat.getDrawable(this, R.drawable.outline_arrow_drop_down)
                binding.instructionArrow.setImageDrawable(drawable)
                binding.instructionDescription.visibility = View.GONE
                isInstructionArrowUp = false
            }
        }

        val instruction =
            recipe.instructions?.let {
                HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            }

        Log.i("summary...", "onCreate: " + instruction)
    }
}