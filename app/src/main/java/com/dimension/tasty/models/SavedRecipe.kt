package com.dimension.tasty.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "saved_recipes")
data class SavedRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val image: String,
    val imageSource: String,
    val title: String,
    val type: String,
    val category: String,
    val ingredients: String,

     val cookingTime: String,
     val instruction: String,
     var favoriteDish: Boolean = false,

) : Serializable
