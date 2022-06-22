package com.homework.food.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homework.food.data.repository.Repository
import com.homework.food.ui.main.viewmodel.FoodViewModel

class FoodViewModelFactory (private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)){
            return FoodViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown View model Class")
    }
}