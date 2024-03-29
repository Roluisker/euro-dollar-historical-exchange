package com.sc.core.model.local

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "timeseries")
open class TimeSeries(
    var start_date: String? = "",
    var end_date: String? = "",
    var rateItemJson: String? = "",
    @Ignore var rateItem: HashMap<String, HashMap<String, String>> = HashMap(),
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
)

