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

    @Query("DELETE FROM food_table")
    suspend fun deleteAll()

    @Query("UPDATE food_table SET favorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id : String , isFavorite : Boolean = true)

    @Query("UPDATE food_table SET favorite = :isFavorite WHERE id = :id")
    suspend fun unsetFavorite(id : String , isFavorite : Boolean = false)

    @Query("UPDATE food_table SET favorite = :item WHERE id = :item")
    suspend fun updateData(item : List<FoodItem>)
}