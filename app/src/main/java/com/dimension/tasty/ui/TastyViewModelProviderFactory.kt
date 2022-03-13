package com.dimension.tasty.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimension.tasty.repository.TastyRepository

class TastyViewModelProviderFactory(
    val tastyRepository: TastyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return TastyViewModel(tastyRepository) as T
    }
}