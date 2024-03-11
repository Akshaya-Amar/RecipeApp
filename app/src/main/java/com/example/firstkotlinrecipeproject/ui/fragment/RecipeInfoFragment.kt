package com.example.firstkotlinrecipeproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.firstkotlinrecipeproject.R
import com.example.firstkotlinrecipeproject.databinding.FragmentRecipeInfoBinding
import com.example.firstkotlinrecipeproject.ui.adapter.SimilarRecipeAdapter
import com.example.firstkotlinrecipeproject.ui.viewmodel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class RecipeInfoFragment : Fragment() {

    private val args: RecipeInfoFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by viewModels()
    private var _binding: FragmentRecipeInfoBinding? = null
    private var scrollToPosition: Runnable? = null
    private val binding get() = _binding!!
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
        viewModel.recipeInfo.observe(viewLifecycleOwner) { recipe ->
            binding.recipe = recipe
        }

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val similarRecipeAdapter by lazy {
            SimilarRecipeAdapter { similarRecipe ->
                Snackbar.make(binding.root, "${similarRecipe.title}", Snackbar.LENGTH_LONG).show()
                binding.recyclerView.removeCallbacks(scrollToPosition)
            }
        }

        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = similarRecipeAdapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = similarRecipeAdapter
        }

        viewModel.getSimilarRecipes(recipeId)
        viewModel.similarRecipes.observe(viewLifecycleOwner) { similarRecipeList ->
            similarRecipeAdapter.submitList(similarRecipeList)

            var currentPosition = 0

//            val scrollToPosition = object : Runnable {
            scrollToPosition = object : Runnable {
                override fun run() {

//                 currentPosition =  (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val nextPosition =
                        if (currentPosition < similarRecipeAdapter.itemCount - 1) currentPosition + 1 else 0

                    currentPosition = nextPosition

                    binding.recyclerView.smoothScrollToPosition(nextPosition)
                    binding.recyclerView.postDelayed(this, 2000)
                }
            }

            binding.recyclerView.postDelayed(scrollToPosition, 2000)

            /*CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    val currentPosition = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val nextPosition = if (currentPosition < similarRecipeAdapter.itemCount - 1) currentPosition + 1 else 0
                    binding.recyclerView.smoothScrollToPosition(nextPosition)
                    delay(1000) // Delay for 1 second
                }
            }*/
        }

        var isSummaryArrowUp = false

        binding.summaryArrow.setOnClickListener {
            if (!isSummaryArrowUp) {
                val drawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.outline_arrow_drop_up
                )
                binding.summaryArrow.setImageDrawable(drawable)
                binding.summaryDescription.visibility = View.VISIBLE
                isSummaryArrowUp = true
            } else {
                val drawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.outline_arrow_drop_down
                )
                binding.summaryArrow.setImageDrawable(drawable)
                binding.summaryDescription.visibility = View.GONE
                isSummaryArrowUp = false
            }
        }

        var isInstructionArrowUp = false

        binding.instructionArrow.setOnClickListener {
            if (!isInstructionArrowUp) {
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_arrow_drop_up)
                binding.instructionArrow.setImageDrawable(drawable)
                binding.instructionDescription.visibility = View.VISIBLE
                isInstructionArrowUp = true
            } else {
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.outline_arrow_drop_down)
                binding.instructionArrow.setImageDrawable(drawable)
                binding.instructionDescription.visibility = View.GONE
                isInstructionArrowUp = false
            }
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