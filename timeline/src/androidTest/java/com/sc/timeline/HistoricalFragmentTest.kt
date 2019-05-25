package com.sc.timeline

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sc.timeline.ui.HistoricalFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoricalFragmentTest {

    @Test
    fun componentsAvailableToUser() {

        launchFragmentInContainer<HistoricalFragment>()

        onView(withId(R.id.startDate))
            .check(matches(isDisplayed()))

        onView(withId(R.id.endDate))
            .check(matches(isDisplayed()))

        onView(withId(R.id.euroTo))
            .check(matches(isDisplayed()))

        onView(withId(R.id.lineChart))
            .check(matches(isDisplayed()))

        onView(withId(R.id.dateText))
            .check(matches(isDisplayed()))

    }

    /*
     */

    @Test
    fun clickT() {

        val scenario = launchFragmentInContainer<HistoricalFragment>()


        onView(withId(R.id.startDate))
            .perform(click())

        var titleId = 0

/*
        scenario.onFragment { fragment ->
            //onView(withId(R.id.startDate))
            //    .perform(click())
            titleId = fragment.activity!!.resources.getIdentifier("alertTitle", "id", "android")
            //onView(isRoot()).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        }*/

        onView(withText("OK"))
            .inRoot(isDialog()) // <---
            .check(matches(isDisplayed()))


    }

}