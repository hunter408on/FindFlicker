package com.cvs.find_flicker.utils

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.cvs.find_flicker.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.TimeoutException

object UITestUtils {
    fun childAtPosition(
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

    fun waitForViewDisappear(id: Int, timeoutSeconds: Long = 10) {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeoutSeconds *1000
        var failed = true
        while(System.currentTimeMillis() < endTime) {
            try {
                val tryView = Espresso.onView(
                    Matchers.allOf(
                        ViewMatchers.withId(id), Matchers.not(
                            ViewMatchers.isDisplayed())))
                tryView.check(ViewAssertions.matches( Matchers.not(ViewMatchers.isDisplayed())))
                return
            } catch (e: NoMatchingViewException) {
                Thread.sleep(300)
            }
        }
        if(failed) throw TimeoutException()
    }

    fun getChildInParentAt(parentId: Int, position: Int): ViewInteraction {
        return Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    ViewMatchers.withId(parentId),
                    position
                ),
                ViewMatchers.isDisplayed()
            )
        )
    }
}