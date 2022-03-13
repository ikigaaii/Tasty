package com.dimension.tasty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimension.tasty.models.MealResponse
import com.dimension.tasty.models.Recipe
import com.dimension.tasty.repository.TastyRepository
import com.dimension.tasty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TastyViewModel(
    val tastyRepository: TastyRepository
): ViewModel() {

    val randomMeal: MutableLiveData<Resource<MealResponse>> = MutableLiveData()

    init {
        getRandomRecipe()
    }

    fun getRandomRecipe() = viewModelScope.launch {
        randomMeal.postValue(Resource.Loading())
        val response = tastyRepository.getRandomRecipe()
        randomMeal.postValue(handleMealResponse(response))
    }

    private fun handleMealResponse(response: Response<MealResponse>) : Resource<MealResponse> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return  Resource.Error(response.message())
    }

}