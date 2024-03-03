package com.example.firstkotlinrecipeproject.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.databinding.RecipeItemBinding
import kotlin.math.log

class RecipeAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var recipeList: List<Recipe>? = null
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<RecipeItemBinding>(
            inflater,
            R.layout.recipe_item, parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        recipeList?.get(position)?.let { recipe ->
            holder.binding.recipe = recipe
            holder.binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return recipeList?.size ?: 0
    }

    fun setRecipeList(recipeList: List<Recipe>) {
        this.recipeList = recipeList
        notifyItemInserted(recipeList.size - 1)
    }

    interface OnItemClickListener {
        fun onItemClicked(recipe: Recipe?) // why nullable?
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(binding.recipe)
                }
            }
        }
    }
}