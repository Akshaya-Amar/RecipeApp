package com.example.firstkotlinrecipeproject.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.SampleSingleton
import com.example.firstkotlinrecipeproject.databinding.FragmentRandomRecipeBinding
import com.example.firstkotlinrecipeproject.ui.adapter.RecipeAdapter
import com.example.firstkotlinrecipeproject.ui.viewmodel.RecipeViewModel

class RandomRecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRandomRecipeBinding? = null
    private val binding get() = _binding!!
    private val TAG = "RandomRecipeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_random_recipe, container, false)
        //        val instance = SampleSingleton.instance
        val instance = SampleSingleton.getInstance()
        Log.i(TAG, "onCreate: $instance")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "")

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.user = "Welcome, \n$name"

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

        /*= RecipeAdapter { recipe ->
            if (recipe.id != null) {
                val action =
                    RandomRecipeFragmentDirections.actionRandomRecipeFragmentToRecipeInfoFragment(
                        recipe.id
                    )
                findNavController().navigate(action)
            }
        }*/

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recipeAdapter
        }

        viewModel.recipeData.observe(viewLifecycleOwner) { data ->
            recipeAdapter.submitList(data.recipes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}