package com.homework.food.ui.main.view

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Observer
import androidx.work.*
import com.homework.food.data.manager.Worker
import com.homework.food.utils.Internet
import java.util.concurrent.TimeUnit

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
        initWork()

    }

    private fun initViewModel() {
        val dao = FoodDB.getDatabase(this).FoodDAO()
        val api = RetrofitInstance.getAPI()
        val repository = Repository(api,dao)
        val factory = FoodViewModelFactory(repository)
        foodViewModel = ViewModelProvider(this, factory)[FoodViewModel::class.java]
//        foodViewModel.syncData(Internet().isOnline(applicationContext))
    }

    private fun initWork(){
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicRequest  = PeriodicWorkRequestBuilder<Worker>(15, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()

//        val periodicRequest  = OneTimeWorkRequestBuilder<Worker>().setInitialDelay(2, TimeUnit.MINUTES)
//            .setConstraints(constraint)
//            .build()

//        WorkManager.getInstance(applicationContext).enqueue(periodicRequest)

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("Test",
            ExistingPeriodicWorkPolicy.KEEP,periodicRequest)

//        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(periodicRequest.id)
//            .observe(this, Observer { info: WorkInfo ->
//                if (info.state == WorkInfo.State.SUCCEEDED) {
//                    WorkManager.getInstance(applicationContext).enqueue(periodicRequest)
//                }
//            })

//        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(periodicRequest.id)
//            .observe(this, Observer { info: WorkInfo ->
//                if (info.state == WorkInfo.State.SUCCEEDED) {
//                    Log.i("vvvvvvvvvvvvvvvv","vvvvvvvvvvvvvv")
//                }
//            })
    }

}

