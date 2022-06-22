package com.homework.food.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.homework.food.data.model.FoodItem

@Dao
interface FoodDAO {
    @Query("SELECT * FROM food_table")
    fun getAll(): LiveData<List<FoodItem>>

    @Insert
    suspend fun insert(foodItem: List<FoodItem>)
}