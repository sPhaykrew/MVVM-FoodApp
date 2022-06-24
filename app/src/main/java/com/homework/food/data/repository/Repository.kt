package com.homework.food.data.repository

import com.homework.food.data.api.FoodApi
import com.homework.food.data.local.FoodDAO
import com.homework.food.data.model.FoodItem

class Repository(private val foodApi : FoodApi , private val foodDAO: FoodDAO) {

    suspend fun getFoodsAPI() = foodApi.getFoods()

    suspend fun storeLocalData(foodItem: List<FoodItem>) = foodDAO.insert(foodItem)

    suspend fun setFavorite(id : String) = foodDAO.setFavorite(id)

    suspend fun unsetFavorite(id : String) = foodDAO.unsetFavorite(id)

    fun getAllFoodsLocal() = foodDAO.getAllFoods()

    fun getFoodLocal(id : String) = foodDAO.getFood(id)

}