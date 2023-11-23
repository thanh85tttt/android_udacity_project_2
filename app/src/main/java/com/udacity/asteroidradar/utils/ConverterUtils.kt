package com.udacity.asteroidradar.utils

import androidx.room.TypeConverter
import java.util.Date

class Converters {
  @TypeConverter
  fun toDate(timestamp: Long?): Date? = timestamp?.let(::Date)

  @TypeConverter
  fun toTimestamp(date: Date?): Long? = date?.time
}
