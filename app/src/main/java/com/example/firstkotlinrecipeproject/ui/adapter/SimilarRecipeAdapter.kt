package com.example.firstkotlinrecipeproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.databinding.SimilarRecipeItemBinding

class SimilarRecipeAdapter : RecyclerView.Adapter<SimilarRecipeAdapter.ViewHolder>() {

    private var recipeList: List<SimilarRecipe>? = null
    private lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SimilarRecipeItemBinding>(
            inflater,
            R.layout.similar_recipe_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val similarRecipe = recipeList?.get(position)
        holder.binding.similarRecipe = similarRecipe
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return recipeList?.size ?: 0
//        return if (recipeList != null) recipeList!!.size else 0
//        return if (recipeList != null) recipeList.size else 0
//        return recipeList.size ?: 0
    }

    inner class ViewHolder(val binding: SimilarRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != null && position != RecyclerView.NO_POSITION) {
                    onClickListener.onItemClick(binding.similarRecipe!!)
                }
            }
        }
    }

    fun setList(recipeList: List<SimilarRecipe>) {
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    fun setOnItemClickListner(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onItemClick(similarRecipe: SimilarRecipe)
    }
}

