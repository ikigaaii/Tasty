package com.dimension.tasty.repository

import com.dimension.tasty.api.RetrofitInstance
import com.dimension.tasty.db.RecipeDataBase
import com.dimension.tasty.models.SavedRecipe
import com.dimension.tasty.util.Constants.Companion.API_KEY
import com.dimension.tasty.util.Constants.Companion.COUNT_OF_SEARCH_RECIPES


class TastyRepository(
    val db: RecipeDataBase
) {

    suspend fun getRandomRecipe() =
        RetrofitInstance.api.getRandomMeal(API_KEY)

    suspend fun searchRecipe(searchQuery: String) =
        RetrofitInstance.api.searchForMeals( API_KEY ,searchQuery, COUNT_OF_SEARCH_RECIPES)

    suspend fun getRecipeById(id: Int) =
        RetrofitInstance.api.getRecipeById(id ,API_KEY)

    suspend fun upsert(recipe: SavedRecipe) = db.getMealDao().upsert(recipe)


    suspend fun getAllRecipes() = db.getMealDao().getAllRecipes()

    suspend fun getSavedRecipesId() = db.getMealDao().getSavedRecipesId()

    suspend fun deleteRecipe(recipe: SavedRecipe) = db.getMealDao().deleteRecipe(recipe)
}

