package com.sc.core.model.local

data class TimeSeries(
    //val rateItem: HashMap<String, ArrayList<String>> = HashMap()
    val rateItem: HashMap<String, HashMap<String, String>> = HashMap()
)
