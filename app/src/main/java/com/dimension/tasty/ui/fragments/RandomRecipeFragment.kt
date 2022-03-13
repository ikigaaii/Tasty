package com.dimension.tasty.ui.fragments

import android.os.Bundle
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


        }

        viewModel.randomMeal.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let { mealResponse ->
                        Glide.with(view).load(mealResponse.meals[0].strMealThumb).into(binding.ivRecipeImage)
                        binding.tvTitle.text = mealResponse.meals[0].strMeal
                        binding.tvType.text = mealResponse.meals[0].strTags
                        binding.tvCategory.text = mealResponse.meals[0].strCategory

                        val str = StringBuilder()

                        str.append(mealResponse.meals[0].strIngredient1 + " " + mealResponse.meals[0].strMeasure1 + "\n")
                        str.append(mealResponse.meals[0].strIngredient2 + " " + mealResponse.meals[0].strMeasure2 + "\n")
                        str.append(mealResponse.meals[0].strIngredient3 + " " + mealResponse.meals[0].strMeasure3 + "\n")
                        str.append(mealResponse.meals[0].strIngredient4 + " " + mealResponse.meals[0].strMeasure4 + "\n")
                        str.append(mealResponse.meals[0].strIngredient5 + " " + mealResponse.meals[0].strMeasure5 + "\n")
                        str.append(mealResponse.meals[0].strIngredient6 + " " + mealResponse.meals[0].strMeasure6 + "\n")
                        str.append(mealResponse.meals[0].strIngredient7 + " " + mealResponse.meals[0].strMeasure7 + "\n")
                        str.append(mealResponse.meals[0].strIngredient8 + " " + mealResponse.meals[0].strMeasure8 + "\n")
                        str.append(mealResponse.meals[0].strIngredient9 + " " + mealResponse.meals[0].strMeasure9 + "\n")
                        str.append(mealResponse.meals[0].strIngredient10 + " " + mealResponse.meals[0].strMeasure10 + "\n")
                        str.append(mealResponse.meals[0].strIngredient11 + " " + mealResponse.meals[0].strMeasure11 + "\n")
                        str.append(mealResponse.meals[0].strIngredient12 + " " + mealResponse.meals[0].strMeasure12 + "\n")
                        str.append(mealResponse.meals[0].strIngredient13 + " " + mealResponse.meals[0].strMeasure13 + "\n")
                        str.append(mealResponse.meals[0].strIngredient14 + " " + mealResponse.meals[0].strMeasure14 + "\n")
                        str.append(mealResponse.meals[0].strIngredient15 + " " + mealResponse.meals[0].strMeasure15 + "\n")
                        str.append(mealResponse.meals[0].strIngredient16 + " " + mealResponse.meals[0].strMeasure16 + "\n")
                        str.append(mealResponse.meals[0].strIngredient17 + " " + mealResponse.meals[0].strMeasure17 + "\n")
                        str.append(mealResponse.meals[0].strIngredient18 + " " + mealResponse.meals[0].strMeasure18 + "\n")
                        str.append(mealResponse.meals[0].strIngredient19 + " " + mealResponse.meals[0].strMeasure19 + "\n")
                        str.append(mealResponse.meals[0].strIngredient20 + " " + mealResponse.meals[0].strMeasure20)
                        binding.tvIngredients.text = str

                        binding.tvCookingInstruction.text = mealResponse.meals[0].strInstructions

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