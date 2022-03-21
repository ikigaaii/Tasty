package com.dimension.tasty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimension.tasty.R
import com.dimension.tasty.adapter.RecipesAdapter
import com.dimension.tasty.databinding.ItemRecipePreviewBinding
import com.dimension.tasty.databinding.SavedRecipeFragmentBinding
import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel

class SavedRecipesFragment : Fragment(R.layout.saved_recipe_fragment) {

    lateinit var viewModel: TastyViewModel
    lateinit var binding: SavedRecipeFragmentBinding
    lateinit var recipesAdapter:  RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SavedRecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

     //   recipesAdapter.setOnItemClickListener {
     //       val bundle = Bundle().apply {
     //           putSerializable("recipe", it)
     //       }
     //       findNavController().navigate(
     //           R.id.action_searchRecipeFragment_to_recipeFragment,
     //           bundle
     //       )
     //   }

    }

    private fun setupRecyclerView(){
        recipesAdapter = RecipesAdapter()
        binding.rvSavedRecipes.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }





}