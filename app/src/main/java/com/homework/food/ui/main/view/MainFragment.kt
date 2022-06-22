package com.homework.food.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.food.R
import com.homework.food.databinding.FragmentMainBinding
import com.homework.food.ui.adapter.RecyclerViewAdapterFood
import com.homework.food.ui.main.viewmodel.FoodViewModel

class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private lateinit var recyclerViewAdapterFood: RecyclerViewAdapterFood
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        initRecyclerView()
        observeData()

        return fragmentMainBinding.root
    }

    private fun initRecyclerView() {
        recyclerViewAdapterFood = RecyclerViewAdapterFood()
        fragmentMainBinding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun observeData(){
        foodViewModel.foods.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapterFood.setItem(it)
            fragmentMainBinding.recyclerview.adapter = recyclerViewAdapterFood
            recyclerViewAdapterFood.notifyDataSetChanged()
        })

        foodViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        foodViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                fragmentMainBinding.progressBar.visibility = View.VISIBLE
            } else {
                fragmentMainBinding.progressBar.visibility = View.GONE
            }
        })

    }

}