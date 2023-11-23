package com.udacity.asteroidradar.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.utils.toDate
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Dao
interface AsteroidDatabaseDao {

  @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date ASC")
  fun getAll(): LiveData<List<AsteroidEntity>>

  @Query(
    "SELECT * FROM asteroid_table WHERE close_approach_date BETWEEN :startDate AND :endDate ORDER BY close_approach_date ASC"
  )
  fun getByWeek(
    startDate: Long = LocalDate.now().toDate().time,
    endDate: Long = LocalDate.now().plusDays(6).toDate().time
  ): LiveData<List<AsteroidEntity>>

  @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :date ORDER BY close_approach_date ASC")
  fun getByDate(date: Long = LocalDate.now().toDate().time): LiveData<List<AsteroidEntity>>

  @Query("SELECT * FROM asteroid_table WHERE id = :key")
  fun getById(key: Long): LiveData<AsteroidEntity>

  @Insert
  suspend fun insert(asteroid: AsteroidEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(vararg asteroid: AsteroidEntity)

  @Update
  suspend fun update(asteroid: AsteroidEntity)

  @Query("DELETE FROM asteroid_table WHERE id = :id")
  suspend fun deleteById(id: String)

  @Query("DELETE FROM asteroid_table")
  suspend fun deleteAll()
}
