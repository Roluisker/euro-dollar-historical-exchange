package com.sc.core.deserializer

import com.google.gson.*
import com.sc.core.CoreConstants.Companion.BASE_LABEL
import com.sc.core.CoreConstants.Companion.END_DATE_LABEL
import com.sc.core.CoreConstants.Companion.RATE_LABEL
import com.sc.core.CoreConstants.Companion.START_DATE_LABEL
import com.sc.core.CoreConstants.Companion.SUCCESS_LABEL
import com.sc.core.CoreConstants.Companion.TIME_SERIES_LABEL
import com.sc.core.model.remote.TimeSeriesRemote
import timber.log.Timber
import java.lang.Exception

import java.lang.reflect.Type

class TimeSeriesDeserializer : JsonDeserializer<TimeSeriesRemote>, JsonSerializer<TimeSeriesRemote> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TimeSeriesRemote {

        val ob = json!!.asJsonObject
        val currentRates = ob.get(RATE_LABEL).asJsonObject

        val seriesRemote = TimeSeriesRemote(
            ob.get(SUCCESS_LABEL).asBoolean, ob.get(TIME_SERIES_LABEL).asString,
            ob.get(START_DATE_LABEL).asString, ob.get(END_DATE_LABEL).asString,
            ob.get(BASE_LABEL).asString
        )

        for (set in currentRates.entrySet()) {
            val rateObjet = set.value.asJsonObject

            try {

                seriesRemote.rates!!.rateItem.put(
                    set.key,
                    arrayListOf(
                        rateObjet.get("USD").asString,
                        rateObjet.get("AUD").asString,
                        rateObjet.get("CAD").asString
                    )
                )
            } catch (error: Exception) {
                Timber.e(error)
            }

        }

        return seriesRemote

    }

    override fun serialize(src: TimeSeriesRemote?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonObject().apply {
            addProperty("BASE", src!!.base)
        }
    }

}