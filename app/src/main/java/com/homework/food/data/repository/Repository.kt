package com.homework.food.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.homework.food.data.api.FoodApi
import com.homework.food.data.local.FoodDAO
import com.homework.food.data.model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val foodApi: FoodApi, private val foodDAO: FoodDAO) {

    //remote
    suspend fun getFoodsAPI() = foodApi.getFoods()

    //local
    fun getAllFoodsByName() = foodDAO.getAllFoodsByName()

    fun getAllFoodsByCal() = foodDAO.getAllFoodsByCal()

    fun getAllFoodsByDiff() = foodDAO.getAllFoodsByDiff()

    fun getFoodLocal(id: String) = foodDAO.getFood(id)

    suspend fun setFavorite(id: String) = foodDAO.setFavorite(id)

    suspend fun unsetFavorite(id: String) = foodDAO.unsetFavorite(id)

    suspend fun delete(foodItem: FoodItem) = foodDAO.delete(foodItem)

    suspend fun callApiAndStore() {
        withContext(Dispatchers.IO) {
            val response = foodApi.getFoods()
            if (response.isSuccessful) {
                response.body()?.let {
                    foodDAO.insert(response.body()!!)
                }
            }
        }
    }

    suspend fun syncData() {
        withContext(Dispatchers.IO) {
            val foodsLocal = foodDAO.getAllFoods()
            var foodsAPI = emptyList<FoodItem>()
            if (foodsLocal.isNotEmpty()) {
                val response = foodApi.getFoods()
                if (response.isSuccessful) {
                    response.body()?.let {
                        foodsAPI = it
                    }
                }
                for (i in foodsLocal.indices){
                    for(j in foodsAPI.indices){
                        if (foodsLocal[i].id == foodsAPI[j].id && foodsLocal[i].favorite){
                            foodsAPI[j].favorite = true
                        }
                    }
                }
                foodDAO.insert(foodsAPI)
            }
        }
    }

}