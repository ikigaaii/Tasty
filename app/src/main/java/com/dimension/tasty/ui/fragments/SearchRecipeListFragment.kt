package com.dimension.tasty.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dimension.tasty.R
import com.dimension.tasty.adapter.RecipesAdapter
import com.dimension.tasty.databinding.ActivityMainBinding
import com.dimension.tasty.databinding.RecipeSearchListFragmentBinding
import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.dimension.tasty.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager

import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat.getSystemService


class SearchRecipeListFragment : Fragment(R.layout.recipe_search_list_fragment) {

    lateinit var viewModel: TastyViewModel
    var binding: RecipeSearchListFragmentBinding? = null

    lateinit var recipesAdapter: RecipesAdapter

      override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          binding = RecipeSearchListFragmentBinding.inflate(inflater, container, false)
          return binding!!.root
      }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        (activity as MainActivity?)?.supportActionBar?.title = "Search Recipes"
        setupRecyclerView()

        recipesAdapter.setOnSearchRecipeItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("recipeId", it.id)
            }
            if (requireActivity() is MainActivity){
                (activity as MainActivity?)?.hideBottomNavigationView()
            }
            findNavController().navigate(
                R.id.action_searchRecipeFragment_to_recipeFragment,
                bundle
            )
        }
        var job : Job?  = null

        binding?.etSearch?.addTextChangedListener{ editable->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchRecipes(editable.toString())}
                }
            }
        }
        binding?.etSearch?.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                (activity as MainActivity).hideKeyboard(view)
                true
            } else {
                false
            }
        }

        viewModel.searchRecipes.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Succes -> {
                    response.data?.let {
                        recipesAdapter.differ.submitList(it.results)
                    }
                }
                is Resource.Error -> {

                    response.message?.let { message ->
                        Log.e("Search Recipe", "Erorr$message")
                    }
                }
            }
        })
    }

    private fun setupRecyclerView(){
        recipesAdapter = RecipesAdapter()
        binding?.rvSearchRecipes?.apply {
            adapter = recipesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}