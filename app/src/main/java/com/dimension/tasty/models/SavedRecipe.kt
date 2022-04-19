package com.dimension.tasty.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "saved_recipes")
data class SavedRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray,
    val title: String,
    val ingredients: String,
    val healthScore: Int,
    val cookingTime: Int,
    val instruction: String

    ) : Serializable
