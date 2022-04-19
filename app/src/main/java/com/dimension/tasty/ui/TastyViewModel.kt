package com.dimension.tasty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimension.tasty.models.RandomRecipeResponse
import com.dimension.tasty.models.Recipe
import com.dimension.tasty.models.SavedRecipe
import com.dimension.tasty.models.SearchRecipeResponse
import com.dimension.tasty.repository.TastyRepository
import com.dimension.tasty.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class TastyViewModel(
    val tastyRepository: TastyRepository
): ViewModel() {

    val randomRecipe: MutableLiveData<Resource<RandomRecipeResponse>> = MutableLiveData()
    val searchRecipes: MutableLiveData<Resource<SearchRecipeResponse>> = MutableLiveData()
    val recipeById: MutableLiveData<Resource<Recipe>> = MutableLiveData()
    val savedRecipe: MutableLiveData<List<SavedRecipe>> = MutableLiveData()
    val savedRecipesId: MutableLiveData<List<Int>> = MutableLiveData()

    init {
        getRandomRecipe()
    }

    fun getRandomRecipe() = viewModelScope.launch {
        getSavedRecipesId().join()
        val response = tastyRepository.getRandomRecipe()

        randomRecipe.postValue(handleRandomRecipeResponse(response))
    }

    fun searchRecipes(str: String) = viewModelScope.launch {
        val response = tastyRepository.searchRecipe(str)
        searchRecipes.postValue(handleSearchRecipeResponse(response))
    }

    fun getRecipeById(id: Int) = viewModelScope.launch {
        getSavedRecipes().join()
        val response = tastyRepository.getRecipeById(id)
        recipeById.postValue(handleRecipeByIdResponse(response))
    }

    fun saveRecipe(recipe: SavedRecipe) = viewModelScope.launch {
        tastyRepository.upsert(recipe)
        getSavedRecipesId().join()
        getSavedRecipes().join()
    }


    fun getSavedRecipes() = viewModelScope.launch {
        savedRecipe.postValue(tastyRepository.getAllRecipes())
    }

    fun getSavedRecipesId() = viewModelScope.launch {
        savedRecipesId.postValue(tastyRepository.getSavedRecipesId())
    }

    fun deleteRecipe(recipe: SavedRecipe) = viewModelScope.launch {
        tastyRepository.deleteRecipe(recipe)
        getSavedRecipesId().join()
    }

    private fun handleRandomRecipeResponse(responseRandom: Response<RandomRecipeResponse>) : Resource<RandomRecipeResponse> {
        if (responseRandom.isSuccessful){
            responseRandom.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(responseRandom.message())
    }

    private fun handleSearchRecipeResponse(responseSearch: Response<SearchRecipeResponse>) : Resource<SearchRecipeResponse> {
        if (responseSearch.isSuccessful){
            responseSearch.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(responseSearch.message())
    }

    private fun handleRecipeByIdResponse(responseSearch: Response<Recipe>) : Resource<Recipe> {
        if (responseSearch.isSuccessful){
            responseSearch.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(responseSearch.message())
    }

}