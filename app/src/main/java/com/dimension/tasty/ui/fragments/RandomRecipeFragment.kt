package com.dimension.tasty.ui.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dimension.tasty.R
import com.dimension.tasty.databinding.RandomRecipeFragmentBinding
import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.dimension.tasty.util.Resource
import java.lang.StringBuilder

class RandomRecipeFragment : Fragment(R.layout.random_recipe_fragment) {
    lateinit var viewModel: TastyViewModel
    lateinit var binding: RandomRecipeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RandomRecipeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel


        binding.btnRefresh.setOnClickListener {

            viewModel.getRandomRecipe()
        }

        viewModel.randomMeal.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let { mealResponse ->
                        val recipe = mealResponse.recipes[0]
                        Glide.with(view).load(recipe.image).into(binding.ivRecipeImage)
                        binding.tvTitle.text = recipe.title
                      //  binding.tvType.text = recipe.healthScore.toString()
                     //   binding.tvCategory.text = mealResponse.recipes[0].strCategory


                        var ingredients = ""

                        for(value in recipe.extendedIngredients){
                            if (ingredients.isEmpty()) {
                                ingredients = value.original
                            } else {
                                ingredients = ingredients + ", \n" + value.original
                            }
                        }

                        binding.tvIngredients.text = ingredients
                        binding.tvCookingInstruction.text =  Html.fromHtml(recipe.instructions)

                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("Random Recipe ", "Error $message")
                    }
                }

            }
        })


    }
}