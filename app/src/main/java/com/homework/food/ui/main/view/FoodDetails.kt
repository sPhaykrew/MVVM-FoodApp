package com.homework.food.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.homework.food.R
import com.homework.food.databinding.FragmentFoodDetailsBinding
import com.homework.food.databinding.FragmentMainBinding
import com.homework.food.ui.main.viewmodel.FoodViewModel
import com.homework.food.utils.loadImage

class FoodDetails : Fragment() {

    private lateinit var fragmentFoodDetailsBinding: FragmentFoodDetailsBinding
    private val food by navArgs<FoodDetailsArgs>()
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFoodDetailsBinding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        getFood()

        fragmentFoodDetailsBinding.favorite.setOnClickListener {
            val isFavorite = food.data.favorite
            if (isFavorite){
                foodViewModel.unsetFavorite(food.data.id)
            } else {
                foodViewModel.setFavorite(food.data.id)
            }
        }

        return fragmentFoodDetailsBinding.root
    }

    private fun getFood(){
        fragmentFoodDetailsBinding.foodImage.loadImage(food.data.image)
        fragmentFoodDetailsBinding.foodName.text = food.data.name
    }

}