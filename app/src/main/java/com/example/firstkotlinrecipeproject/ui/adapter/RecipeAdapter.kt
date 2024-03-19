package com.example.firstkotlinrecipeproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.databinding.RecipeItemBinding

class RecipeAdapter(
    private val onClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeViewHolder>(COMPARATOR) {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }

    /*
        for multiple view type
        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RecipeItemBinding>(
            inflater,
            R.layout.recipe_item,
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.recipe = getItem(position)
        holder.binding.executePendingBindings()
        holder.bind(onClick = onClick)
    }
}

class RecipeViewHolder(val binding: RecipeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(onClick: (Recipe) -> Unit) {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (binding.recipe != null) {
                    onClick.invoke(binding.recipe!!)
                }
            }
        }
    }
}