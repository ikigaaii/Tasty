package com.dimension.tasty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dimension.tasty.R
import com.dimension.tasty.databinding.RecipeFragmentBinding

import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder

class RecipeFragment : Fragment(R.layout.recipe_fragment) {

    lateinit var viewModel: TastyViewModel
    lateinit var binding: RecipeFragmentBinding
    val args: RecipeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

       // val recipe = args.recipe


      //  Glide.with(view).load(recipe.strMealThumb).into(binding.ivRecipeImage)
      //  binding.tvTitle.text = recipe.strMeal
      //  binding.tvType.text = recipe.strTags
      //  binding.tvCategory.text = recipe.strCategory
//
      //  val str = StringBuilder()
      //  str.append(recipe.strIngredient1 + " " + recipe.strMeasure1 + "\n")
      //  str.append(recipe.strIngredient2 + " " + recipe.strMeasure2 + "\n")
      //  str.append(recipe.strIngredient3 + " " + recipe.strMeasure3 + "\n")
      //  str.append(recipe.strIngredient4 + " " + recipe.strMeasure4 + "\n")
      //  str.append(recipe.strIngredient5 + " " + recipe.strMeasure5 + "\n")
      //  str.append(recipe.strIngredient6 + " " + recipe.strMeasure6 + "\n")
      //  str.append(recipe.strIngredient7 + " " + recipe.strMeasure7 + "\n")
      //  str.append(recipe.strIngredient8 + " " + recipe.strMeasure8 + "\n")
      //  str.append(recipe.strIngredient9 + " " + recipe.strMeasure9 + "\n")
      //  str.append(recipe.strIngredient10 + " " + recipe.strMeasure10 + "\n")
      //  str.append(recipe.strIngredient11 + " " + recipe.strMeasure11 + "\n")
      //  str.append(recipe.strIngredient12 + " " + recipe.strMeasure12 + "\n")
      //  str.append(recipe.strIngredient13 + " " + recipe.strMeasure13 + "\n")
      //  str.append(recipe.strIngredient14 + " " + recipe.strMeasure14 + "\n")
      //  str.append(recipe.strIngredient15 + " " + recipe.strMeasure15 + "\n")
      //  str.append(recipe.strIngredient16 + " " + recipe.strMeasure16 + "\n")
      //  str.append(recipe.strIngredient17 + " " + recipe.strMeasure17 + "\n")
      //  str.append(recipe.strIngredient18 + " " + recipe.strMeasure18 + "\n")
      //  str.append(recipe.strIngredient19 + " " + recipe.strMeasure19 + "\n")
      //  str.append(recipe.strIngredient20 + " " + recipe.strMeasure20)
      //  binding.tvIngredients.text = str
//
      //  binding.tvCookingInstruction.text = recipe.strInstructions




    }
}