package com.sc.timeline

import android.os.Bundle
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
    fun mainComponentsAvailable() {

        launchFragmentInContainer<HistoricalFragment>(Bundle(), R.style.Theme_AppCompat)

        onView(withId(R.id.lineChart))
            .check(matches(isDisplayed()))

        onView(withId(R.id.dateSelector))
            .check(matches(isDisplayed()))

    }

    @Test
    fun dateDialogAvailable() {

        launchFragmentInContainer<HistoricalFragment>(Bundle(), R.style.Theme_AppCompat)

        onView(withId(R.id.dateSelector))
            .perform(click())

        onView(withText("OK"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

    }

}