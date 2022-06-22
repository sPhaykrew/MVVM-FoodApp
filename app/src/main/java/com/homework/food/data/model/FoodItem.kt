package com.homework.food.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Food_Table")
@Parcelize
data class FoodItem(
    @PrimaryKey val id: String,
    @ColumnInfo val calories: String,
    @ColumnInfo val carbos: String,
    @ColumnInfo val description: String,
    @ColumnInfo val difficulty: Int,
    @ColumnInfo val fats: String,
    @ColumnInfo val headline: String,
    @ColumnInfo val image: String,
    @ColumnInfo val name: String,
    @ColumnInfo val proteins: String,
    @ColumnInfo val thumb: String,
    @ColumnInfo val time: String
) : Parcelable