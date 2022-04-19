package com.dimension.tasty.models

data class SearchRecipeResponse(
    val number: Int,
    val offset: Int,
    val results: MutableList<Result>,
    val totalResults: Int
)