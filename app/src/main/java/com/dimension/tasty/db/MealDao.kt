package com.dimension.tasty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dimension.tasty.models.Recipe

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: Recipe): Long

    @Query("Select * from recipes")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}