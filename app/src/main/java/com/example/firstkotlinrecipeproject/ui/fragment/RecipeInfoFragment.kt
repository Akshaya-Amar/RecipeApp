package com.example.firstkotlinrecipeproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.SnapHelper
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.data.model.Recipe
import com.example.firstkotlinrecipeproject.data.model.SimilarRecipe
import com.example.firstkotlinrecipeproject.databinding.FragmentRecipeInfoBinding
import com.example.firstkotlinrecipeproject.ui.adapter.SimilarRecipeAdapter
import com.example.firstkotlinrecipeproject.ui.viewmodel.RecipeViewModel
import com.example.firstkotlinrecipeproject.util.Response
import com.google.android.material.snackbar.Snackbar

class RecipeInfoFragment : Fragment() {

    private val args: RecipeInfoFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRecipeInfoBinding? = null
    private var scrollToPosition: Runnable? = null
    private val binding get() = _binding!!
    private val similarRecipeAdapter by lazy {
        SimilarRecipeAdapter { similarRecipe ->
            Snackbar.make(binding.root, "${similarRecipe.title}", Snackbar.LENGTH_LONG)
                .show()
            binding.recyclerView.removeCallbacks(scrollToPosition)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_info,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId = args.id

//        args.recipe.id?.let { viewModel.getRecipeInf(it) }
        viewModel.getRecipeInfo(recipeId)
        viewModel.recipeInfo.observe(viewLifecycleOwner) {
            handleRecipeInfoResponse(it)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarRecipeAdapter
        }

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        viewModel.getSimilarRecipes(recipeId)
        viewModel.similarRecipes.observe(viewLifecycleOwner) {
            handleSimilarRecipesResponse(it)
        }

        binding.summaryArrow.setOnClickListener {
            toggleDescriptionVisibility(binding.summaryArrow, binding.summaryDescription)
        }

        binding.instructionArrow.setOnClickListener {
            toggleDescriptionVisibility(
                binding.instructionArrow,
                binding.instructionDescription
            )
        }
    }

    private fun handleRecipeInfoResponse(response: Response<Recipe>) {
        when (response) {
            is Response.Loading -> {
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
            }

            is Response.Success -> {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.relativeLayout.visibility = View.VISIBLE
                binding.recipe = response.data
            }

            is Response.Error -> {
                binding.shimmerLayout.visibility = View.GONE
                binding.shimmerLayout.stopShimmer()
                Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        viewModel.getRecipeInfo(args.id)
                    }
                    .show()
            }
        }
    }

    private fun handleSimilarRecipesResponse(response: Response<List<SimilarRecipe>>) =
        with(binding) {
            when (response) {
                is Response.Loading -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }

                is Response.Success -> {

                    similarRecipeAdapter.submitList(response.data)

                    var currentPosition = 0

                    scrollToPosition = object : Runnable {
                        override fun run() {

                            val nextPosition =
                                if (currentPosition < similarRecipeAdapter.itemCount - 1) currentPosition + 1 else 0

                            currentPosition = nextPosition

                            recyclerView.smoothScrollToPosition(nextPosition)
                            recyclerView.postDelayed(this, 2000)
                        }
                    }

                    recyclerView.postDelayed(scrollToPosition, 2000)

                    val onScrollListener = object : OnScrollListener() {
                        override fun onScrollStateChanged(
                            recyclerView: RecyclerView,
                            newState: Int
                        ) {
                            if (newState == SCROLL_STATE_DRAGGING) {
                                binding.recyclerView.removeCallbacks(scrollToPosition)
                                binding.recyclerView.removeOnScrollListener(this)
                            }
                        }
                    }

                    binding.recyclerView.addOnScrollListener(onScrollListener)
                }

                is Response.Error -> {
                    binding.shimmerLayout.visibility = View.GONE
                    binding.shimmerLayout.stopShimmer()
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Retry") {
                            viewModel.getSimilarRecipes(args.id)
                        }
                        .show()
                }
            }
        }

    private fun toggleDescriptionVisibility(arrowImageView: ImageView, description: TextView) {
        if (description.isVisible) {
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.outline_arrow_drop_down)
            arrowImageView.setImageDrawable(drawable)
            description.visibility = View.GONE
        } else {
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.outline_arrow_drop_up)
            arrowImageView.setImageDrawable(drawable)
            description.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        binding.recyclerView.removeCallbacks(scrollToPosition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}