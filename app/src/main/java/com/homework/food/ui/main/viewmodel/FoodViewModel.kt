package com.homework.food.ui.main.viewmodel

import androidx.lifecycle.*
import com.homework.food.data.model.FoodItem
import com.homework.food.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: Repository) : ViewModel() {
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val getAllFoodsByName: LiveData<List<FoodItem>> = repository.getAllFoodsByName()
    val getAllFoodsByCal: LiveData<List<FoodItem>> = repository.getAllFoodsByCal()
    val getAllFoodsByDiff: LiveData<List<FoodItem>> = repository.getAllFoodsByDiff()

    var sortValue : MutableLiveData<String> = MutableLiveData("byName")


    fun getFood(id : String) : LiveData<FoodItem> = repository.getFoodLocal(id)

    fun callAPI(isOnline: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            loading.value = true
            if (isOnline) {
                repository.storeLocalData()
                loading.value = false
            } else {
                loading.value = false
                onError("Error : No internet")
            }
        }
    }

    fun setFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(getAllFoodsByName.value!![getAllFoodsByName.value!!.size-1])
            repository.setFavorite(id)
        }
    }

    fun unsetFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unsetFavorite(id)
        }
    }

//    fun syncData(isOnline: Boolean) {
//        viewModelScope.launch(Dispatchers.IO) {
//            while (true) {
//                delay(8000)
//                if (isOnline) {
//                    var list = emptyList<FoodItem>()
//                    val response = repository.getFoodsAPI()
//                    if (response.isSuccessful) {
//                        response.body()?.let {
//                            list = it
//                        }
//                        for (i in 0 until getAllFoods.value!!.size) {
//                            if (getAllFoods.value!![i].favorite) {
//                                list[i].favorite = true
//                            }
//                        }
//                    }
//                    repository.storeLocalData(list)
//                } else {
//                    Log.d("Error","No internet")
//                }
//            }
//        }
//    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

}