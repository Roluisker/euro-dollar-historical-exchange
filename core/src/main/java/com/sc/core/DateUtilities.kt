package com.sc.core

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

const val SIMPLE_TIME_FORMAT_DATE = "dd MMM"
const val TODAY_TIME_FORMAT_DATE = "Y-MM-dd"

object DateUtilities {

    fun format(format: String, dateTime: DateTime): String {

        return try {

            DateTimeFormat.forPattern(format)
                .withZone(DateTimeZone.getDefault())
                .withLocale(Locale.getDefault())
                .print(dateTime)

        } catch (error: Exception) {
            CoreConstants.EMPTY
        }

    }

    fun todayMinusDays(daysToMinus: Int): String {
        var today = DateTime()
        var todayMinus = today.minusDays(daysToMinus)
        return todayMinus.toString(TODAY_TIME_FORMAT_DATE)
    }

    fun nativeCalendarDaysAgo(daysAgo: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        return calendar
    }

    fun nativeCalendarToday(): Calendar = Calendar.getInstance()

}