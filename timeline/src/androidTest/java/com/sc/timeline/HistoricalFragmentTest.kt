package com.sc.timeline

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sc.timeline.ui.HistoricalFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoricalFragmentTest {

    @Test
    fun testEventX() {

        @Test
        fun testEventFragment() {

            launchFragmentInContainer<HistoricalFragment>()

            onView(withId(R.id.startDate))
                .check(matches(isDisplayed()))

        }

    }

}