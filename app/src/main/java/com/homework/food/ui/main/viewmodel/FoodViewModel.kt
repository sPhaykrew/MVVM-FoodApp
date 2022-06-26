package com.homework.food.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.food.data.model.FoodItem
import com.homework.food.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: Repository) : ViewModel() {
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val getAllFoodsByName: LiveData<List<FoodItem>> = repository.getAllFoodsByName()
    val getAllFoodsByCal: LiveData<List<FoodItem>> = repository.getAllFoodsByCal()
    val getAllFoodsByDiff: LiveData<List<FoodItem>> = repository.getAllFoodsByDiff()
    var sortValue: MutableLiveData<String> = MutableLiveData("byName")


    fun getFood(id: String): LiveData<FoodItem> = repository.getFoodLocal(id)

    fun callAPI(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                loading.value = true
                if (isOnline) {
                    repository.callApiAndStore()
                    loading.value = false
                } else {
                    loading.value = false
                    onError("Error : No internet")
                }
            } catch (e: Exception) {
                onError("Error : ${e.message}")
            }

        }
    }

    fun setFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(getAllFoodsByName.value!![getAllFoodsByName.value!!.size - 1])
            repository.setFavorite(id)
        }
    }

    fun unsetFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unsetFavorite(id)
        }
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    fun syncData(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(8000)
                if (isOnline) {
                    var list = emptyList<FoodItem>()
                    val response = repository.getFoodsAPI()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            list = it
                        }
                        for (i in 0 until getAllFoodsByName.value!!.size) {
                            if (getAllFoodsByName.value!![i].favorite) {
                                list[i].favorite = true
                            }
                        }
                    }
                    repository.storeLocalData_(list)
                } else {
                    Log.d("Error", "No internet")
                }
            }
        }
    }

}