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

    private val recipeAdapter by lazy {
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

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }

        viewModel.recipeData.observe(viewLifecycleOwner) { resp ->
            when (resp) {
                is Response.Loading -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }

                is Response.Error -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    Snackbar.make(binding.root, resp.message.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Retry") {
                            viewModel.getRecipes()
                        }
                        .show()
                }

                is Response.Success -> {
                    recipeAdapter.submitList(resp.data)
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}