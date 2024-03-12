package com.example.firstkotlinrecipeproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.databinding.FragmentRandomRecipeBinding
import com.example.firstkotlinrecipeproject.ui.adapter.RecipeAdapter
import com.example.firstkotlinrecipeproject.ui.viewmodel.RecipeViewModel
import com.example.firstkotlinrecipeproject.util.Response
import com.google.android.material.snackbar.Snackbar

class RandomRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRandomRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_random_recipe, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        /*= RecipeAdapter { recipe ->
            if (recipe.id != null) {
                val action =
                    RandomRecipeFragmentDirections.actionRandomRecipeFragmentToRecipeInfoFragment(
                        recipe.id
                    )
                findNavController().navigate(action)
            }
        }*/

        val recipeAdapter by lazy {
            RecipeAdapter { recipe ->
                if (recipe.id != null) {
                    val action =
                        RandomRecipeFragmentDirections.actionRandomRecipeFragmentToRecipeInfoFragment(
                            recipe.id
                        )
                    findNavController().navigate(action)
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recipeAdapter
        }

        viewModel.recipeData.observe(viewLifecycleOwner) { resp ->
            when (resp) {
                is Response.Error -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    Snackbar.make(binding.root, resp.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                }

                is Response.Loading -> {

                }

                is Response.Success -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    recipeAdapter.submitList(resp.data)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}