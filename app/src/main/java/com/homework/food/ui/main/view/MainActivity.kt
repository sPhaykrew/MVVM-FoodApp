package com.homework.food.ui.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.homework.food.R
import com.homework.food.data.api.RetrofitInstance
import com.homework.food.data.local.FoodDB
import com.homework.food.data.repository.Repository
import com.homework.food.databinding.ActivityMainBinding
import com.homework.food.ui.base.FoodViewModelFactory
import com.homework.food.ui.main.viewmodel.FoodViewModel
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var foodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        initViewModel()

    }

    private fun initViewModel() {
        val dao = FoodDB.getDatabase(this).FoodDAO()
        val repository = Repository(RetrofitInstance.getAPI(), dao)
        val factory = FoodViewModelFactory(repository)
        foodViewModel = ViewModelProvider(this, factory)[FoodViewModel::class.java]
//        foodViewModel.syncData(Internet().isOnline(applicationContext))
    }
}

