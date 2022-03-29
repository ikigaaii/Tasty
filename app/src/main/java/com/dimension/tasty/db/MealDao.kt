package com.dimension.tasty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dimension.tasty.models.SavedRecipe

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: SavedRecipe): Long

    @Query("Select * from saved_recipes")
    fun getAllRecipes(): LiveData<List<SavedRecipe>>

    @Delete
    suspend fun deleteRecipe(recipe: SavedRecipe)
}