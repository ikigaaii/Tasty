package com.dimension.tasty.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.dimension.tasty.models.SavedRecipe

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: SavedRecipe): Long

    @Query("Select * from saved_recipes")
    suspend fun getAllRecipes(): List<SavedRecipe>

    @Query("Select id from saved_recipes")
    suspend fun getSavedRecipesId(): List<Int>


    @Delete
    suspend fun deleteRecipe(recipe: SavedRecipe)
}