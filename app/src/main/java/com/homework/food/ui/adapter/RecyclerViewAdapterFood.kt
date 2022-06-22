package com.homework.food.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homework.food.data.model.FoodItem
import com.homework.food.databinding.RecyclerviewFoodsBinding
import com.homework.food.utils.loadImage

class RecyclerViewAdapterFood : RecyclerView.Adapter<RecyclerViewAdapterFood.ViewHolder>() {

    private var item : List<FoodItem> = emptyList()

    fun setItem(item : List<FoodItem>){
        this.item = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapterFood.ViewHolder {
        return ViewHolder(RecyclerviewFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterFood.ViewHolder, position: Int) {
        holder.binding.image.loadImage(item[position].thumb)
        holder.binding.name.text = item[position].name
    }

    override fun getItemCount(): Int = item.size

    inner class ViewHolder(val binding: RecyclerviewFoodsBinding) : RecyclerView.ViewHolder(binding.root)

}