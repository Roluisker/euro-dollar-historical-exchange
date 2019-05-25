package com.sc.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sc.core.model.local.TimeSeries

@Dao
interface TimeSeriesDao {

    @Query("SELECT * FROM TimeSeries WHERE start_date = :startDate AND end_date = :endDate")
    suspend fun seriesByDates(startDate: String, endDate: String): TimeSeries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(movies: TimeSeries)

    @Query("DELETE FROM TimeSeries")
    suspend fun dropSeries(): Int

    @Query("SELECT * FROM TimeSeries")
    suspend fun series(): List<TimeSeries>

}