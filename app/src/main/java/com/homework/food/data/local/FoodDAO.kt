package com.homework.food.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.homework.food.data.api.FoodApi
import com.homework.food.data.model.FoodItem

@Dao
interface FoodDAO {
    @Query("SELECT * FROM food_table ORDER BY name ASC")
    fun getAllFoodsByName(): LiveData<List<FoodItem>>

    @Query("SELECT * FROM food_table ORDER BY calories ASC")
    fun getAllFoodsByCal(): LiveData<List<FoodItem>>

    @Query("SELECT * FROM food_table ORDER BY difficulty ASC")
    fun getAllFoodsByDiff(): LiveData<List<FoodItem>>

    @Query("SELECT * FROM food_table where id = :id")
    fun getFood(id : String): LiveData<FoodItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodItem: List<FoodItem>)

    @Query("UPDATE food_table SET favorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id : String , isFavorite : Boolean = true)

    @Query("UPDATE food_table SET favorite = :isFavorite WHERE id = :id")
    suspend fun unsetFavorite(id : String , isFavorite : Boolean = false)

    @Delete
    suspend fun delete(foodItem: FoodItem)

}