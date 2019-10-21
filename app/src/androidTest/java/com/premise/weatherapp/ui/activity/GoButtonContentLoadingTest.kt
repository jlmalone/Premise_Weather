package com.premise.weatherapp.ui.activity


import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.premise.weatherapp.R
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.Matchers.`is`

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GoButtonContentLoadingTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun goButtonContentLoadingTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.input_location_edittext),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("boston"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.go), withText("Enter"),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.weather),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))
        val timeUpdatedTextView: TextView = mActivityTestRule.activity.findViewById(R.id.time_updated)

       assert( timeUpdatedTextView.text.contains("Updated") && timeUpdatedTextView.text.length>16)



        val textView3 = onView(
            allOf(
                withId(R.id.info),
                isDisplayed()
            )
        )

        val locationName: TextView = mActivityTestRule.activity.findViewById(R.id.info)
//        onView(timeUpdatedTextView).check()

        assert( timeUpdatedTextView.text.contains("Locat")  && timeUpdatedTextView.text.contains(".") && timeUpdatedTextView.text.length>16)
        textView3.check(matches(isDisplayed()))

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
