package com.homework.food.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.homework.food.R
import com.homework.food.databinding.FragmentFoodDetailsBinding
import com.homework.food.ui.main.viewmodel.FoodViewModel
import com.homework.food.utils.loadImageCircle

class FoodDetails : Fragment() {

    private lateinit var fragmentFoodDetailsBinding: FragmentFoodDetailsBinding
    private val food by navArgs<FoodDetailsArgs>()
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFoodDetailsBinding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        initToolbar()
        initFood()

        return fragmentFoodDetailsBinding.root
    }

    private fun initToolbar(){
        fragmentFoodDetailsBinding.toolbarSub.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_foodDetails_to_mainFragment)
        }
    }

    private fun initFood(){
        val foodID = food.data.id
        foodViewModel.getFood(foodID).observe(viewLifecycleOwner, Observer {
            fragmentFoodDetailsBinding.foodImage.loadImageCircle(it.image)
            fragmentFoodDetailsBinding.foodName.text = it.name
            fragmentFoodDetailsBinding.headline.text = it.headline
            fragmentFoodDetailsBinding.description.text = it.description

            //cardView
            fragmentFoodDetailsBinding.calorieValue.text = it.calories.split(" ")[0]
            fragmentFoodDetailsBinding.carbosValue.text = it.carbos.split(" ")[0]
            fragmentFoodDetailsBinding.difficultyValue.text = it.difficulty.toString()
            fragmentFoodDetailsBinding.fatsValue.text = it.fats.split(" ")[0]
            fragmentFoodDetailsBinding.proteinsValue.text = it.proteins.split(" ")[0]
            fragmentFoodDetailsBinding.timeValue.text = it.time

            val isFavorite = it.favorite
            if (isFavorite){
                fragmentFoodDetailsBinding.favoriteImage.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            } else {
                fragmentFoodDetailsBinding.favoriteImage.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }

            fragmentFoodDetailsBinding.favorite.setOnClickListener {
                if (isFavorite){
                    foodViewModel.unsetFavorite(foodID)
                } else {
                    foodViewModel.setFavorite(foodID)
                }
            }
        })
    }

}