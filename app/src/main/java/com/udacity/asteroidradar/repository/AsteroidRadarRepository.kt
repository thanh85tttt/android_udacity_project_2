package com.udacity.asteroidradar.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.toAsteroidModel
import com.udacity.asteroidradar.toAsteroidEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

@RequiresApi(Build.VERSION_CODES.O)
class AsteroidRadarRepository(private val ct: Context, asteroidDatabase: AsteroidDatabase) {

  private val dao = asteroidDatabase.asteroidDatabaseDao;
  val getByDate = dao.getByDate().map { it.toAsteroidModel() }
  val getByWeek = dao.getByWeek().map { it.toAsteroidModel() }
  val getAll = dao.getAll().map { it.toAsteroidModel() }

  suspend fun asteroidRadarAPI() = withContext(Dispatchers.IO) {
    try {
      MutableLiveData(Network.neoApiService.getDatePictureApi())
    } catch (e: Exception) {
      Log.e("Error occur when fetch day picture:", e.printStackTrace().toString())
      MutableLiveData()
    }
  }

  suspend fun fetchNewData() = withContext(Dispatchers.IO) {
    try {
      dao.insertAll(
        *parseAsteroidsJsonResult(JSONObject(Network.neoApiService.getFeedApi()))
          .toAsteroidEntity().toTypedArray()
      )
    } catch (e: Exception) {
      dao.insertAll(
        *parseAsteroidsJsonResult(
          JSONObject(
            readDataFromJsonFile("androidjson.json")
          )
        )
          .toAsteroidEntity().toTypedArray()
      )
      Log.e("Error occur when fetch new data:", e.printStackTrace().toString())
    }
  }

  private fun readDataFromJsonFile(path: String): String {
    return try {
      val ipStream = ct.assets.open(path)
      val br = BufferedReader(InputStreamReader(ipStream))
      br.useLines { lines ->
        lines.joinToString(separator = "")
      }
    } catch (e: Exception) {
      e.printStackTrace()
      ""
    }
  }
}
