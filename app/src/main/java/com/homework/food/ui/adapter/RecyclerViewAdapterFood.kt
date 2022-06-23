package com.homework.food.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.homework.food.R
import com.homework.food.data.model.FoodItem
import com.homework.food.databinding.RecyclerviewFoodsBinding
import com.homework.food.ui.main.view.MainFragmentDirections
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

        if (item[position].favorite){
            holder.binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        } else {
            holder.binding.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }

        holder.binding.root.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToFoodDetails(item[position])
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int = item.size

    inner class ViewHolder(val binding: RecyclerviewFoodsBinding) : RecyclerView.ViewHolder(binding.root)

}