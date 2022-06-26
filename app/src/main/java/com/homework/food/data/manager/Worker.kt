package com.homework.food.data.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.homework.food.R
import com.homework.food.data.api.RetrofitInstance
import com.homework.food.data.local.FoodDB
import com.homework.food.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Worker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val dao = FoodDB.getDatabase(applicationContext).FoodDAO()
            val api = RetrofitInstance.getAPI()
            val repository = Repository(api, dao)
            repository.syncData()
            Log.e("check_worker", "success")
            notification(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            Result.failure()
        }
    }

    private fun notification(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "syncData"
            val descriptionText = "content"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Food App")
            .setContentText("Sync data complete")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(applicationContext).notify(1, builder.build())
    }

}