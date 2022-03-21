package com.dimension.tasty.ui.fragments

import androidx.core.widget.addTextChangedListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimension.tasty.R
import com.dimension.tasty.adapter.RecipesAdapter
import com.dimension.tasty.databinding.RecipeSearchFragmentBinding
import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.dimension.tasty.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchRecipeFragment : Fragment(R.layout.recipe_search_fragment) {

    lateinit var viewModel: TastyViewModel
    lateinit var binding: RecipeSearchFragmentBinding

    lateinit var recipesAdapter: RecipesAdapter

      override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          binding = RecipeSearchFragmentBinding.inflate(inflater, container, false)
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

        var job : Job?  = null
        binding.etSearch.addTextChangedListener{ editable->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchRecipes(editable.toString())}
                }
            }
        }

      //  viewModel.searchMeals.observe(viewLifecycleOwner, Observer { response ->
      //      when(response){
      //          is Resource.Succes -> {
      //              response.data?.let {
      //                  recipesAdapter.differ.submitList(it.meals.toList())
//
      //              }
      //          }
      //          is Resource.Error -> {
//
      //              response.message?.let { message ->
      //                  Log.e("Search Recipe", "Erorr$message")
      //              }
      //          }
      //      }
      //  })
    }

    private fun setupRecyclerView(){
        recipesAdapter = RecipesAdapter()
        binding.rvSearchRecipes.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}