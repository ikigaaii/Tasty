package com.dimension.tasty.api

import com.dimension.tasty.models.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TastyApi  {

    @GET("random.php")
    suspend fun getRandomMeal(
        ): Response<MealResponse>

    @GET("search.php")
    suspend fun searchForMeals(
        @Query("s") searchQuery: String
    ): Response<MealResponse>


}