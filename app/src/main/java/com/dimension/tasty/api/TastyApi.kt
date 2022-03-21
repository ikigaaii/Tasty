package com.dimension.tasty.api

import com.dimension.tasty.models.MealResponse
import com.dimension.tasty.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TastyApi  {

    @GET("random")
    suspend fun getRandomMeal(
        @Query("apiKey") apiKey: String = API_KEY
        ): Response<MealResponse>

    @GET("search.php")
    suspend fun searchForMeals(
        @Query("s") searchQuery: String
    ): Response<MealResponse>


}