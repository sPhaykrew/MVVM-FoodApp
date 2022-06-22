package com.homework.food.data.repository

import com.homework.food.data.api.FoodApi
import com.homework.food.data.local.FoodDAO
import com.homework.food.data.model.FoodItem

class Repository(private val foodApi : FoodApi , private val foodDAO: FoodDAO) {

    suspend fun getFoodsAPI() = foodApi.getFoods()

    fun getFoodsLocal() = foodDAO.getAll()

    suspend fun insertFoods(foodItem: List<FoodItem>) = foodDAO.insert(foodItem)

}