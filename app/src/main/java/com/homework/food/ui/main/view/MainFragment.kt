package com.homework.food.ui.main.view

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.food.R
import com.homework.food.databinding.FragmentMainBinding
import com.homework.food.ui.adapter.RecyclerViewAdapterFood
import com.homework.food.ui.main.viewmodel.FoodViewModel
import com.homework.food.utils.Internet
import com.homework.food.utils.Prefs

class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private lateinit var recyclerViewAdapterFood: RecyclerViewAdapterFood
    private lateinit var myPrefs: Prefs
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        getPrefs()
        initRecyclerView()
        observeData()
        sorting()

        return fragmentMainBinding.root
    }

    private fun getPrefs(){
        myPrefs = Prefs(requireContext())
        val mySort = myPrefs.getPrefs()
        foodViewModel.sortValue.postValue(mySort)
    }

    private fun sorting() {
        fragmentMainBinding.sorting.setOnClickListener {
            val menu = PopupMenu(requireContext(), it)
            menu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    (R.id.byName) -> {
                        val sorting = "byName"
                        foodViewModel.sortValue.postValue(sorting)
                        myPrefs.savePrefs(sorting)
                    }
                    (R.id.byCal) -> {
                        val sorting = "byCal"
                        foodViewModel.sortValue.postValue(sorting)
                        myPrefs.savePrefs(sorting)
                    }
                    (R.id.byDiff) -> {
                        val sorting = "byDiff"
                        foodViewModel.sortValue.postValue(sorting)
                        myPrefs.savePrefs(sorting)
                    }
                    (R.id.byFavor) -> {
                        val sorting = "byFavor"
                        foodViewModel.sortValue.postValue(sorting)
                        myPrefs.savePrefs(sorting)
                    }
                }
                true
            }
            menu.inflate(R.menu.sort_menu)
            menu.show()
        }
    }

    private fun initRecyclerView() {
        recyclerViewAdapterFood = RecyclerViewAdapterFood()
        fragmentMainBinding.recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun observeData() {

        foodViewModel.sortValue.observe(viewLifecycleOwner,Observer{
            when(it){
                "byFavor" -> {
                    foodViewModel.getAllFoodsByFavor.observe(viewLifecycleOwner, Observer { item ->
                        if (item.isNotEmpty()) {
                            recyclerViewAdapterFood.setItem(item)
                            recyclerViewAdapterFood.notifyDataSetChanged()
                            fragmentMainBinding.recyclerview.adapter = recyclerViewAdapterFood
                        } else {
                            foodViewModel.callAPI(Internet().isOnline(requireContext()))
                        }
                    })
                }
                "byCal" -> {
                    foodViewModel.getAllFoodsByCal.observe(viewLifecycleOwner, Observer { item ->
                        if (item.isNotEmpty()) {
                            recyclerViewAdapterFood.setItem(item)
                            recyclerViewAdapterFood.notifyDataSetChanged()
                            fragmentMainBinding.recyclerview.adapter = recyclerViewAdapterFood
                        } else {
                            foodViewModel.callAPI(Internet().isOnline(requireContext()))
                        }
                    })
                }
                "byDiff" -> {
                    foodViewModel.getAllFoodsByDiff.observe(viewLifecycleOwner, Observer { item ->
                        if (item.isNotEmpty()) {
                            recyclerViewAdapterFood.setItem(item)
                            recyclerViewAdapterFood.notifyDataSetChanged()
                            fragmentMainBinding.recyclerview.adapter = recyclerViewAdapterFood
                        } else {
                            foodViewModel.callAPI(Internet().isOnline(requireContext()))
                        }
                    })
                }
                else -> {
                    foodViewModel.getAllFoodsByName.observe(viewLifecycleOwner, Observer { item ->
                        if (item.isNotEmpty()) {
                            recyclerViewAdapterFood.setItem(item)
                            recyclerViewAdapterFood.notifyDataSetChanged()
                            fragmentMainBinding.recyclerview.adapter = recyclerViewAdapterFood
                        } else {
                            foodViewModel.callAPI(Internet().isOnline(requireContext()))
                        }
                    })
                }
            }
        })



        foodViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
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