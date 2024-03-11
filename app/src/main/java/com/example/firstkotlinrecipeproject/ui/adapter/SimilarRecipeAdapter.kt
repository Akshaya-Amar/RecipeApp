package com.example.firstkotlinrecipeproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.databinding.SimilarRecipeItemBinding

class SimilarRecipeAdapter(
    private val onClick: (SimilarRecipe) -> Unit
) : ListAdapter<SimilarRecipe, SimilarRecipeAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<SimilarRecipe>() {
            override fun areItemsTheSame(oldItem: SimilarRecipe, newItem: SimilarRecipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SimilarRecipe,
                newItem: SimilarRecipe
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
    /*companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<SimilarRecipe>() {
            override fun areContentsTheSame(
                oldItem: SimilarRecipe,
                newItem: SimilarRecipe
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: SimilarRecipe, newItem: SimilarRecipe): Boolean {
                return oldItem == newItem
            }
        }
    }*/

//    private var recipeList: List<SimilarRecipe>? = null
//    private lateinit var onClickListener: OnClickListener

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
        val similarRecipe = getItem(position)
        holder.binding.similarRecipe = similarRecipe
        holder.binding.executePendingBindings()
    }

    /* override fun getItemCount(): Int {
         return recipeList?.size ?: 0
 //        return if (recipeList != null) recipeList!!.size else 0
 //        return if (recipeList != null) recipeList.size else 0
 //        return recipeList.size ?: 0
     }*/

    inner class ViewHolder(val binding: SimilarRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick.invoke(binding.similarRecipe!!)
//                    onClickListener.onItemClick(binding.similarRecipe!!)
                }
            }
        }
    }

    /*fun setList(recipeList: List<SimilarRecipe>) {
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    fun setOnItemClickListner(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onItemClick(similarRecipe: SimilarRecipe)
    }*/
}

