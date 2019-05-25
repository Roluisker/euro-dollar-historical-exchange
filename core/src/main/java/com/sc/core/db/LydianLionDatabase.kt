package com.sc.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sc.core.model.local.TimeSeries

@Database(entities = [TimeSeries::class], version = 2, exportSchema = false)
abstract class LydianLionDatabase : RoomDatabase() {
    abstract fun timeSeriesDao(): TimeSeriesDao
}