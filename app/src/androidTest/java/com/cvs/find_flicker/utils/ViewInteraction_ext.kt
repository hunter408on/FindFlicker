package com.cvs.find_flicker.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.action.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.cvs.find_flicker.utils.recyclerview_testutils.RecyclerViewMatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import java.util.concurrent.TimeoutException


private val TIMEOUT_LONG: Long = 40
private val TIMEOUT_MEDIUM: Long = 10
private val TIMEOUT_SHORT: Long = 2

//region === Checks ===
fun ViewInteraction.checkExistence(timeoutSeconds: Long = TIMEOUT_MEDIUM): ViewInteraction {

    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeoutSeconds *1000
    var failed = true
    while(System.currentTimeMillis() < endTime) {
        try {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            return this
        } catch (e: Exception) {
            Thread.sleep(50)
        }
    }
    if(failed) throw TimeoutException()
    return this
}

fun ViewInteraction.checkExistenceAfterDelay(): ViewInteraction {
    Thread.sleep(TIMEOUT_SHORT * 1000)
    return checkExistence()
}

fun ViewInteraction.checkNotDisplayed(timeout: Long = TIMEOUT_MEDIUM): ViewInteraction {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeout *1000
    var failed = true
    while(System.currentTimeMillis() < endTime) {
        try {
            check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
            return this
        } catch (e: NoMatchingViewException) {
            Thread.sleep(50)
        }
    }
    if(failed) throw TimeoutException()
    return this
}

fun ViewInteraction.checkDoesNotExist(): ViewInteraction {
    check(ViewAssertions.doesNotExist())
    return this
}

fun ViewInteraction.checkVisibility(visibility: ViewMatchers.Visibility): ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility)))
    return this
}

fun ViewInteraction.checkClickable(): ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.isClickable()))
    return this
}

fun ViewInteraction.checkNotClickable(): ViewInteraction {
    check(ViewAssertions.matches(not(ViewMatchers.isClickable())))
    return this
}

fun ViewInteraction.checkText(expected: String): ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.withText(expected)))
    return this
}

fun ViewInteraction.checkTextNotEqual(wrong: String): ViewInteraction {
    check(ViewAssertions.matches(not(ViewMatchers.withText(wrong))))
    return this
}

fun ViewInteraction.checkTextDescendant(expected: String): ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(expected))))
    return this
}

fun ViewInteraction.checkTextNotEmpty(): ViewInteraction {
    check(ViewAssertions.matches(not(ViewMatchers.withText(""))))
    return this
}

fun ViewInteraction.checkTextNotEmptyDescendant(): ViewInteraction {
    check(ViewAssertions.matches(not(ViewMatchers.hasDescendant(ViewMatchers.withText("")))))
    return this
}

fun ViewInteraction.checkRecyclerViewItemText(position: Int, expected: String): ViewInteraction {
    check(ViewAssertions.matches(atPosition(position, ViewMatchers.hasDescendant(ViewMatchers.withText(expected)))))
    return this
}

fun ViewInteraction.checkRecyclerViewItemTextNotEmpty(position: Int): ViewInteraction {
    check(ViewAssertions.matches(atPosition(position, ViewMatchers.hasDescendant(not(ViewMatchers.withText(""))))))
    return this
}

fun ViewInteraction.checkDisabled(): ViewInteraction {
    check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isEnabled())))
    return this
}

fun ViewInteraction.checkEnabled() : ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    return this
}

fun ViewInteraction.checkSwitchOn() : ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.isChecked()))
    return this
}

fun ViewInteraction.checkSwitchOff() : ViewInteraction {
    check(ViewAssertions.matches(ViewMatchers.isNotChecked()))
    return this
}
//endregion

fun ViewInteraction.getText(): String? {
    var text: String? = null
    this.perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Get text of the view"
        }

        override fun perform(uiController: UiController?, view: View?) {
            view?.let {
                val tv = it as TextView
                text = tv.text.toString()
            }
        }
    })
    return text
}

//region === Actions ===
fun ViewInteraction.click(): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.click())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.clickImmediate(): ViewInteraction {
    perform(ViewActions.click())
    return this
}

private fun ViewInteraction.tryUntil(timeoutSeconds: Long = 5, task: () -> Unit): Boolean {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeoutSeconds *1000
    var success = false
    while(System.currentTimeMillis() < endTime) {
        try {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            task.invoke()
            success = true
            break
        } catch (e: Exception) {
            Thread.sleep(50)
        }
    }
    return success
}

fun ViewInteraction.clickCoordinate(x: Int, y: Int) {
    val success = tryUntil {
        perform(clickIn(x, y))
    }
    if(!success) throw TimeoutException()
}

private fun clickIn(x: Int, y: Int): ViewAction {
    return GeneralClickAction(
            Tap.SINGLE,
            CoordinatesProvider { view ->
                val screenPos = IntArray(2)
                view.getLocationOnScreen(screenPos)
                val screenX = (screenPos[0] + x).toFloat()
                val screenY = (screenPos[1] + y).toFloat()
                floatArrayOf(screenX, screenY)
            },
            Press.FINGER)
}

private fun swipeRightFromCenter(): ViewAction {
    return GeneralSwipeAction(
            Swipe.FAST,
            GeneralLocation.CENTER,
            GeneralLocation.CENTER_RIGHT,
            Press.FINGER)
}

private fun swipeLeftFromCenter(): ViewAction {
    return GeneralSwipeAction(
            Swipe.FAST,
            GeneralLocation.CENTER,
            GeneralLocation.CENTER_LEFT,
            Press.FINGER)
}

fun ViewInteraction.typeText(text: String): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.clearText() : ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.replaceText(text: String): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.clickRecyclerViewItem(position: Int): ViewInteraction {
    val success = tryUntil {
        perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click()))
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.clickRecyclerViewItemCoordinate(position: Int, x: Int, y: Int): ViewInteraction {
    val success = tryUntil {
        perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, clickIn(x, y)))
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.swipeLeftRecyclerViewItem(position: Int): ViewInteraction {
    val success = tryUntil {
        perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, swipeLeftFromCenter()))
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.swipeRightRecyclerViewItem(position: Int): ViewInteraction {
    val success = tryUntil {
        perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, swipeRightFromCenter()))
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.swipeUp(): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.swipeUp())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.swipeUpALot(): ViewInteraction {
    swipeUp()
    swipeUp()
    swipeUp()
    swipeUp()
    swipeUp()
    swipeUp()
    swipeUp()
    return this
}

fun ViewInteraction.swipeDown(): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.swipeDown())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.swipeLeft(): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.swipeLeft())
    }
    if(!success) throw TimeoutException()
    return this
}

fun ViewInteraction.swipeRight(): ViewInteraction {
    val success = tryUntil {
        perform(ViewActions.swipeRight())
    }
    if(!success) throw TimeoutException()
    return this
}

fun getRecyclerViewItem(recyclerViewId: Int, itemPosition: Int, viewId: Int): ViewInteraction {
    return Espresso.onView(RecyclerViewMatcher(recyclerViewId).atPositionOnView(itemPosition, viewId))
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {

    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}
//endregion