package com.homework.food.data.api

import com.homework.food.data.model.FoodItem
import com.homework.food.utils.Constants.Companion.GET_FOOD
import retrofit2.Response
import retrofit2.http.GET

interface FoodApi {
    @GET(GET_FOOD)
    suspend fun getFoods(): Response<List<FoodItem>>
}