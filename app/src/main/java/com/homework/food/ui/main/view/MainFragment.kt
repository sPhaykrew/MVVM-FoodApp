package com.homework.food.ui.main.view

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.food.R
import com.homework.food.databinding.FragmentMainBinding
import com.homework.food.ui.adapter.RecyclerViewAdapterFood
import com.homework.food.ui.main.viewmodel.FoodViewModel
import com.homework.food.utils.Internet

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
        sorting()

        return fragmentMainBinding.root
    }

    private fun sorting() {
        fragmentMainBinding.sorting.setOnClickListener {
            val menu = PopupMenu(requireContext(), it)
            menu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    (R.id.byName) -> Toast.makeText(requireContext(), "byName", Toast.LENGTH_LONG)
                        .show()
                    (R.id.byCal) -> Toast.makeText(requireContext(), "byCal", Toast.LENGTH_LONG)
                        .show()
                    (R.id.byDiff) -> Toast.makeText(requireContext(), "byDiff", Toast.LENGTH_LONG)
                        .show()
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
        foodViewModel.getAllFoods.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                recyclerViewAdapterFood.setItem(it)
                fragmentMainBinding.recyclerview.adapter = recyclerViewAdapterFood
                recyclerViewAdapterFood.notifyDataSetChanged()
            } else {
                foodViewModel.callAPI(Internet().isOnline(requireContext()))
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