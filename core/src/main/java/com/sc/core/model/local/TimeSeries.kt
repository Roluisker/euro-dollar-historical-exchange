package com.sc.core.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeseries")
open class TimeSeries(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val start_date: String,
    val end_date: String,
    var rateItem: HashMap<String, HashMap<String, String>> = HashMap()
)
