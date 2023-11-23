package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
  abstract val asteroidDatabaseDao: AsteroidDatabaseDao
}

object DatabaseBuilder {
  private var INSTANCE: AsteroidDatabase? = null

  fun getDB(context: Context): AsteroidDatabase =
    INSTANCE ?: synchronized(this) {
      INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
    }

  private fun buildDatabase(context: Context) =
    Room.databaseBuilder(
      context.applicationContext,
      AsteroidDatabase::class.java,
      "asteroid_database"
    )
      .fallbackToDestructiveMigration()
      .build()
}
