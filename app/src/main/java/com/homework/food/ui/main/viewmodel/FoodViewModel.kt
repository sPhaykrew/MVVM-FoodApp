package com.homework.food.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.food.data.model.FoodItem
import com.homework.food.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(private val repository: Repository) : ViewModel() {
    val foods : MutableLiveData<List<FoodItem>> = MutableLiveData()
    val errorMessage : MutableLiveData<String> = MutableLiveData()
    val loading : MutableLiveData<Boolean> = MutableLiveData()

    init {
        getFoodsAPI()
    }

    fun getFoodsLocal() : LiveData<List<FoodItem>> = repository.getFoodsLocal()

    fun insert(foodItem: List<FoodItem>){
        viewModelScope.launch {Dispatchers.IO
            repository.insertFoods(foodItem)
        }
    }

    private fun getFoodsAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getFoodsAPI()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    foods.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}