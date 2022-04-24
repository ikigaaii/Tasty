package com.dimension.tasty.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dimension.tasty.R
import com.dimension.tasty.databinding.RecipeFragmentBinding

import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class SavedRecipeFragment : Fragment(R.layout.recipe_fragment) {

    lateinit var viewModel: TastyViewModel
    lateinit var binding: RecipeFragmentBinding
    val args: SavedRecipeFragmentArgs by navArgs()
    private lateinit var recipesId: List<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.GONE
        setHasOptionsMenu(true)
        binding.btnRefresh.visibility = View.GONE
        viewModel = (activity as MainActivity).viewModel
        viewModel.getSavedRecipesId()
        viewModel.savedRecipesId.observe(viewLifecycleOwner) {
            recipesId = it

            val recipe = args.recipe
            binding.ivRecipeImage.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    recipe.image,
                    0,
                    recipe.image.size
                )
            )
            binding.tvTitle.text = recipe.title
            binding.tvHealthScore.text = "Health Score: " + recipe.healthScore
            binding.tvReadyInMinutes.text =
                "Ready in minutes: " + recipe.cookingTime
            binding.tvIngredients.text = recipe.ingredients
            binding.tvCookingInstruction.text = Html.fromHtml(recipe.instruction)
            if (recipesId.contains(recipe.id)) {
                binding.ivFavoriteRecipe.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favorite_selected
                    )
                )
            }

            binding.ivFavoriteRecipe.setOnClickListener {


                if (recipesId.contains(recipe.id)) {
                    viewModel.deleteRecipe(recipe)
                    Snackbar.make(view, "Recipe deleted", Snackbar.LENGTH_SHORT).show()
                    binding?.ivFavoriteRecipe?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.ic_favorite_unselected
                        )
                    )
                } else {
                    viewModel.saveRecipe(recipe)
                    Snackbar.make(view, "Recipe saved", Snackbar.LENGTH_SHORT).show()
                    binding!!.ivFavoriteRecipe.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.ic_favorite_selected
                        )
                    )
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        onDestroy()
    }
}