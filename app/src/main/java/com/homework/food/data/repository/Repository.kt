package com.homework.food.data.repository

import com.homework.food.data.api.FoodApi
import com.homework.food.data.local.FoodDAO
import com.homework.food.data.model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val foodApi: FoodApi, private val foodDAO: FoodDAO) {

    suspend fun getFoodsAPI() = foodApi.getFoods()

    suspend fun setFavorite(id: String) = foodDAO.setFavorite(id)

    suspend fun unsetFavorite(id: String) = foodDAO.unsetFavorite(id)

    fun getAllFoodsByName() = foodDAO.getAllFoodsByName()

    fun getAllFoodsByCal() = foodDAO.getAllFoodsByCal()

    fun getAllFoodsByDiff() = foodDAO.getAllFoodsByDiff()

    fun getFoodLocal(id: String) = foodDAO.getFood(id)

    suspend fun delete(foodItem: FoodItem) = foodDAO.delete(foodItem)

    suspend fun storeLocalData() {
        withContext(Dispatchers.IO) {
            val response = foodApi.getFoods()
            if (response.isSuccessful) {
                response.body()?.let {
                    foodDAO.insert(response.body()!!)
                }
            }
        }
    }

    suspend fun storeLocalData(foodItem: List<FoodItem>) = foodDAO.insert(foodItem)

}