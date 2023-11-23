package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.DatabaseBuilder
import com.udacity.asteroidradar.repository.AsteroidRadarRepository
import com.udacity.asteroidradar.utils.AsteroidRadarFilter
import com.udacity.asteroidradar.utils.AsteroidRadarFilter.ALL_TIME
import com.udacity.asteroidradar.utils.AsteroidRadarFilter.WEEKLY
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(app: Application) : AndroidViewModel(app) {

  private val repository = AsteroidRadarRepository(app.applicationContext, DatabaseBuilder.getDB(app))

  private val _dayPicture = MutableLiveData<PictureOfDay>()

  private val _filter = MutableLiveData(ALL_TIME)

  val dayPicture: LiveData<PictureOfDay> get() = _dayPicture

  val asteroids: LiveData<List<Asteroid>> = _filter.switchMap { filter: AsteroidRadarFilter ->
    filterAsteroid(filter)
  }

  fun settingFilter(filter: AsteroidRadarFilter) {
    _filter.value = filter
  }

  private fun filterAsteroid(filter: AsteroidRadarFilter): LiveData<List<Asteroid>> {
    return when (filter) {
      WEEKLY -> repository.getByWeek
      ALL_TIME -> repository.getAll
      else -> repository.getByDate
    }
  }

  init {
    viewModelScope.launch {
      repository.fetchNewData()
      _dayPicture.value = repository.asteroidRadarAPI().value
    }
  }
}
