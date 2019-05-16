package com.sc.core.deserializer

import com.google.gson.*
import com.sc.core.model.remote.TimeSeriesRemote

import java.lang.reflect.Type

class TimeSeriesDeserializer : JsonDeserializer<TimeSeriesRemote>, JsonSerializer<TimeSeriesRemote> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TimeSeriesRemote {

        val ob = json!!.asJsonObject

        return TimeSeriesRemote(
            ob.get("success").asBoolean, ob.get("timeseries").asString,
            ob.get("start_date").asString, ob.get("end_date").asString,
            ob.get("base").asString
        )

    }

    override fun serialize(src: TimeSeriesRemote?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {

        return JsonObject().apply {
            addProperty("BASE", src!!.base)
        }

        /*

         JsonObject object = new JsonObject();
         object.add("ID", src.getID());
         object.add("name", src.getName());
         object.add("price", (src.getPrice() / src.getQuantity));
         object.add("quantity", src.getQuantity());
         return object;

         */

    }

}