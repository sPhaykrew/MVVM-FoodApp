package com.homework.food.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.homework.food.data.model.FoodItem
import com.homework.food.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(private val repository: Repository) : ViewModel() {
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        syncData()
    }

    fun getFoods(): LiveData<List<FoodItem>> = repository.getFoodsLocal()

    fun storeData(isFirst: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFirst) {
                val response = repository.getFoodsAPI()
                if (response.isSuccessful) {
                    response.body()?.let {
                        repository.storeLocalData(it)
                        withContext(Dispatchers.Main) {
                            loading.value = false
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Error : ${response.message()} ")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    loading.value = false
                }
            }
        }
    }

    fun setFavorite(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavorite(id)
        }
    }

    fun unsetFavorite(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.unsetFavorite(id)
        }
    }

    private fun syncData() {
        viewModelScope.launch(Dispatchers.IO) {
            var i = 0
            while (true) {
                delay(5000)
                val response = repository.getFoodsAPI()
                if (response.isSuccessful) {
                    response.body()?.let {
                        repository.updateData(it)
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}