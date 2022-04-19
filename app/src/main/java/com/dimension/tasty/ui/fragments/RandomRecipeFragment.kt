package com.dimension.tasty.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dimension.tasty.R
import com.dimension.tasty.models.Recipe
import com.dimension.tasty.models.SavedRecipe
import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.dimension.tasty.util.Resource
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dimension.tasty.databinding.RecipeFragmentBinding


class RandomRecipeFragment : Fragment(R.layout.recipe_fragment) {
    private lateinit var viewModel: TastyViewModel
    private var binding: RecipeFragmentBinding? = null
    private lateinit var saveRecipe: SavedRecipe
    private lateinit var recipe: Recipe
    private var ingredients = ""
    private lateinit var recipesId: List<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel
        (activity as MainActivity?)?.supportActionBar?.title = "Random Recipe"
        binding?.btnRefresh?.setOnClickListener {
            binding?.ivFavoriteRecipe?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favorite_unselected
                )
            )
            viewModel.getRandomRecipe()
        }

        viewModel.randomRecipe.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let { mealResponse ->
                        recipe = mealResponse.recipes[0]

                        binding?.let {
                            Glide.with(view).load(recipe.image)
                                .listener(object : RequestListener<Drawable> {
                                    override fun onLoadFailed(
                                        e: GlideException?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        isFirstResource: Boolean
                                    ): Boolean {
                                        getView()?.let { it1 -> Snackbar.make(it1, "Image Load Erorr", Snackbar.LENGTH_SHORT).show() }
                                        viewModel.getRandomRecipe()
                                        return true
                                    }

                                    override fun onResourceReady(
                                        resource: Drawable?,
                                        model: Any?,
                                        target: Target<Drawable>?,
                                        dataSource: DataSource?,
                                        isFirstResource: Boolean
                                    ): Boolean {

                                        val stream = ByteArrayOutputStream()
                                        val bitmap = resource?.toBitmap()
                                        binding?.ivRecipeImage?.setImageBitmap(bitmap)
                                        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                        val image = stream.toByteArray()

                                        saveRecipe = SavedRecipe(
                                            mealResponse.recipes[0].id,
                                            image,
                                            mealResponse.recipes[0].title,
                                            ingredients,
                                            mealResponse.recipes[0].healthScore,
                                            mealResponse.recipes[0].readyInMinutes,
                                            mealResponse.recipes[0].instructions
                                        )

                                        if (recipesId.contains(saveRecipe.id)) {
                                            binding?.ivFavoriteRecipe?.setImageDrawable(
                                                ContextCompat.getDrawable(
                                                    requireActivity(),
                                                    R.drawable.ic_favorite_selected
                                                )
                                            )
                                        }

                                        return true
                                    }

                                }).into(it.ivRecipeImage)
                        }
                        binding?.tvTitle?.text = recipe.title
                        binding?.tvHealthScore?.text = "Health Score: " + recipe.healthScore
                        binding?.tvReadyInMinutes?.text = "Ready in minutes: " + recipe.readyInMinutes


                        for (value in recipe.extendedIngredients) {
                            if (ingredients.isEmpty()) {
                                ingredients = value.original
                            } else {
                                ingredients = ingredients + ", \n" + value.original
                            }
                        }
                        binding?.tvIngredients?.text = ingredients
                        binding?.tvCookingInstruction?.text = Html.fromHtml(recipe.instructions)

                        binding?.ivFavoriteRecipe?.setOnClickListener {

                            if (recipesId.contains(saveRecipe.id)) {
                                viewModel.deleteRecipe(saveRecipe)
                                Snackbar.make(view, "Recipe deleted", Snackbar.LENGTH_SHORT).show()
                                binding?.ivFavoriteRecipe?.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireActivity(),
                                        R.drawable.ic_favorite_unselected
                                    )
                                )
                            } else {
                                viewModel.saveRecipe(saveRecipe)
                                Snackbar.make(view, "Recipe saved", Snackbar.LENGTH_SHORT).show()
                                binding!!.ivFavoriteRecipe.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireActivity(),
                                        R.drawable.ic_favorite_selected
                                    )
                                )
                            }

                            viewModel.saveRecipe(saveRecipe)
                            viewModel.getSavedRecipes()
                        }
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("Random Recipe ", "Error $message")
                    }
                }
            }
        })
        viewModel.savedRecipesId.observe(viewLifecycleOwner, Observer {
            recipesId = it
        })
    }
    fun setupFavButton(){

    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}