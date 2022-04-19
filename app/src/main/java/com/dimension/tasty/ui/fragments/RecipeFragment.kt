package com.dimension.tasty.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dimension.tasty.R
import com.dimension.tasty.databinding.RecipeFragmentBinding
import com.dimension.tasty.models.SavedRecipe

import com.dimension.tasty.ui.MainActivity
import com.dimension.tasty.ui.TastyViewModel
import com.dimension.tasty.util.Resource
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

class RecipeFragment : Fragment(R.layout.recipe_fragment) {

    lateinit var viewModel: TastyViewModel
    var binding: RecipeFragmentBinding? = null
    val args: RecipeFragmentArgs by navArgs()
    private lateinit var recipes: List<SavedRecipe>
    private var ingredients = ""
    private lateinit var saveRecipe: SavedRecipe

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



        viewModel.getRecipeById(args.recipeId)

        viewModel.savedRecipe.observe(viewLifecycleOwner, {
            recipes = it
        })

        viewModel.recipeById.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Succes -> {
                    response.data?.let { recipe ->


                        binding?.let {
                            Glide.with(view).load(recipe.image).listener(object :
                                RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    getView()?.let { it1 -> Snackbar.make(it1, "Image Load Erorr", Snackbar.LENGTH_SHORT).show() }
                                    if (requireActivity() is MainActivity){
                                        (activity as MainActivity?)?.showBottomNavigationView()
                                    }
                                    findNavController().navigateUp()
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
                                    binding!!.ivRecipeImage.setImageBitmap(bitmap)
                                    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                    val image = stream.toByteArray()

                                    saveRecipe = SavedRecipe(recipe.id,
                                        image, recipe.title, ingredients, recipe.healthScore,
                                        recipe.readyInMinutes, recipe.instructions)

                                    if(recipes.contains(saveRecipe)){
                                        binding!!.ivFavoriteRecipe.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                requireActivity(),
                                                R.drawable.ic_favorite_selected
                                            ))
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

                            if (recipes.contains(saveRecipe)) {
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
                                binding?.ivFavoriteRecipe?.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireActivity(),
                                        R.drawable.ic_favorite_unselected
                                    )
                                )
                            }
                            viewModel.saveRecipe(saveRecipe)
                        }

                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("Recipe ", "Error $message")
                    }
                }
            }
        })


    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}