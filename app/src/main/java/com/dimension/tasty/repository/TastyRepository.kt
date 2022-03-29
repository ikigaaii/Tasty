package com.dimension.tasty.repository

import com.dimension.tasty.api.RetrofitInstance
import com.dimension.tasty.db.RecipeDataBase
import com.dimension.tasty.models.SavedRecipe


class TastyRepository(
    val db: RecipeDataBase
) {

    suspend fun getRandomRecipe() =
        RetrofitInstance.api.getRandomMeal()

  //  suspend fun searchRecipe(searchQuery: String) =
  //      RetrofitInstance.api.searchForMeals(searchQuery)

    suspend fun upsert(recipe: SavedRecipe) = db.getMealDao().upsert(recipe)

    fun getAllRecipes() = db.getMealDao().getAllRecipes()

    suspend fun deleteRecipe(recipe: SavedRecipe) = db.getMealDao().deleteRecipe(recipe)
}

