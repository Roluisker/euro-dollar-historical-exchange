package com.sc.core

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sc.core.deserializer.TimeSeriesDeserializer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.reflect.Type

@RunWith(JUnit4::class)
class TimeSeriesDeserializerTest {

    @MockK
    lateinit var json: JsonElement

    @MockK
    lateinit var typeOfT: Type

    @MockK
    lateinit var context: JsonDeserializationContext

    private var timeSeriesDeserializer = TimeSeriesDeserializer()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun addition_isCorrect() {
        timeSeriesDeserializer.deserialize(json, typeOfT, context)

        //var parser = JsonParser()
        //parser.parse("").asJsonObject

        // JsonObject("")
         every { json.asJsonObject } returns JsonParser().parse("").asJsonObject

        Assert.assertEquals(4, 2 + 2)
    }

}