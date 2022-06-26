package com.homework.food.data.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.homework.food.data.api.RetrofitInstance
import com.homework.food.data.local.FoodDB
import com.homework.food.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Worker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context,workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
//            val dao = FoodDB.getDatabase(applicationContext).FoodDAO()
//            val api = RetrofitInstance.getAPI()
//            val repository = Repository(api,dao)
//            repository.syncData()
            Log.e("check_worker","success")
            Result.success()
        } catch (e: Exception){
            Log.e("Error",e.message.toString())
            Result.failure()
        }
    }

}