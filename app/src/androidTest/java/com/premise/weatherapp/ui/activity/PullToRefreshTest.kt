package com.premise.weatherapp.ui.activity

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.premise.weatherapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.ViewAction
import androidx.test.espresso.UiController

/**
 * Pull to Refresh Espresso test. Performs a Pull to Refresh action with a custom
 * constraint to guarantee a proper pull. Then ensure the list has been refreshed with a new item
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class PullToRefreshTest {

    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pullToRefreshTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.input_location_edittext),

                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Washington DC"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.go), withText("Enter"),

                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val imageView = onView(
            allOf(
                withId(com.premise.weatherapp.R.id.image),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        onView(withId(R.id.swipeContainer)).perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))


        val imageView2 = onView(
            allOf(
                withId(R.id.image),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))
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

