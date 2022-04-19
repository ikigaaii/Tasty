package com.dimension.tasty.ui.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.dimension.tasty.R
import com.dimension.tasty.adapter.SavedRecipesAdapter

import com.dimension.tasty.databinding.SavedRecipeListFragmentBinding
import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.google.android.material.snackbar.Snackbar


class SavedRecipeListFragment : Fragment(R.layout.saved_recipe_list_fragment) {

    lateinit var viewModel: TastyViewModel
    var binding: SavedRecipeListFragmentBinding? = null
    lateinit var recipesAdapter:  SavedRecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SavedRecipeListFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity?)?.supportActionBar?.title = "Saved Recipes"
        viewModel = (activity as MainActivity).viewModel
        viewModel.getSavedRecipes()
        setupRecyclerView()

        recipesAdapter?.setOnSavedRecipeItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("recipe", it)
            }
            if (requireActivity() is MainActivity){
                (activity as MainActivity?)?.hideBottomNavigationView()
            }
            findNavController().navigate(
                R.id.action_savedRecipesFragment_to_savedRecipeFragment,
                bundle
            )
        }

        viewModel.savedRecipe.observe(viewLifecycleOwner, Observer {
            recipesAdapter?.differ?.submitList(it)
        })


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val recipe = recipesAdapter.differ.currentList[position]
                viewModel.deleteRecipe(recipe)
                Snackbar.make(view, "Successfully deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveRecipe(recipe)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding?.rvSavedRecipes)
        }
    }

    private fun setupRecyclerView(){
        recipesAdapter = SavedRecipesAdapter()
        binding?.rvSavedRecipes?.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(activity)
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
}