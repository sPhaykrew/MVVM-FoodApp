package com.homework.food.data.api

import com.homework.food.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private const val api_url = BASE_URL

        fun getAPI(): FoodApi {
            return Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApi::class.java)
        }
    }
}