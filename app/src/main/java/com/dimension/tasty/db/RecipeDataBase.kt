package com.dimension.tasty.db

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dimension.tasty.models.SavedRecipe


@Database(
    entities = [SavedRecipe::class],
    version = 1
)


abstract class RecipeDataBase : RoomDatabase() {

    abstract fun getMealDao(): MealDao



    companion object{
        @Volatile
        private var instance: RecipeDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDataBase(context).also { instance  = it}
        }
        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RecipeDataBase::class.java,
                "recipe_db.db"
            ).build()
    }
}