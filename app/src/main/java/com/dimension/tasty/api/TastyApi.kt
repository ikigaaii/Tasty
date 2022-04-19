package com.dimension.tasty.api

import com.dimension.tasty.models.RandomRecipeResponse
import com.dimension.tasty.models.Recipe
import com.dimension.tasty.models.SearchRecipeResponse
import com.dimension.tasty.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TastyApi  {

    @GET("random")
    suspend fun getRandomMeal(
        @Query("apiKey") apiKey: String
        ): Response<RandomRecipeResponse>

    @GET("complexSearch")
    suspend fun searchForMeals(
        @Query("apiKey") apiKey: String,
        @Query("query") searchQuery: String,
        @Query("number") numberOfRecipes: String
    ): Response<SearchRecipeResponse>

    @GET("{id}/information")
    suspend fun getRecipeById(
        @Path("id") time: Int,
        @Query("apiKey") apiKey: String
    ): Response<Recipe>

}