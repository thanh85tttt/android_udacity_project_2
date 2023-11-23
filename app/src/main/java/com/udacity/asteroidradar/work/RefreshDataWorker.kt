package com.udacity.asteroidradar.work

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.DatabaseBuilder
import com.udacity.asteroidradar.repository.AsteroidRadarRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
  CoroutineWorker(appContext, params) {

  companion object {
    const val WORK_NAME = "RefreshDataWorker"
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun doWork(): Result = try {
    AsteroidRadarRepository(applicationContext, DatabaseBuilder.getDB(applicationContext)).fetchNewData()
    Result.success()
  } catch (e: HttpException) {
    Result.retry()
  }
}
