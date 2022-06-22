package com.homework.food.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.food.data.model.FoodItem

@Database(entities = [FoodItem::class], version = 1)
abstract class FoodDB : RoomDatabase()  {
    abstract fun FoodDAO() : FoodDAO
    companion object{
        @Volatile
        private var INSTANCE : FoodDB? = null
        fun getDatabase(context: Context): FoodDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodDB::class.java,
                        "food_database"
                    ).build()
                }
                return instance
            }
        }
    }
}