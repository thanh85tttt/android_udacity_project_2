package com.udacity.asteroidradar

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.utils.toDate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
  val id: Long,
  val codename: String,
  val closeApproachDate: String,
  val absoluteMagnitude: Double,
  val estimatedDiameter: Double,
  val relativeVelocity: Double,
  val distanceFromEarth: Double,
  val isPotentiallyHazardous: Boolean
) : Parcelable

@RequiresApi(Build.VERSION_CODES.O)
fun List<Asteroid>.toAsteroidEntity(): List<AsteroidEntity> {
  return map {
    AsteroidEntity(
      id = it.id,
      codename = it.codename,
      closeApproachDate = it.closeApproachDate.toDate(),
      absoluteMagnitude = it.absoluteMagnitude,
      estimatedDiameter = it.estimatedDiameter,
      relativeVelocity = it.relativeVelocity,
      distanceFromEarth = it.distanceFromEarth,
      isPotentiallyHazardous = it.isPotentiallyHazardous
    )
  }
}