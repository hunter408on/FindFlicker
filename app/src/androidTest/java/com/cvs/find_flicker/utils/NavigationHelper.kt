package com.cvs.find_flicker.utils

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import com.cvs.find_flicker.R
import com.cvs.find_flicker.photos.PhotosListViewIds
import com.cvs.find_flicker.utils.UITestUtils.waitForViewDisappear
import org.hamcrest.Matchers

object NavigationHelper {
    fun openPhotoDetail() {
        waitForViewDisappear(PhotosListViewIds.ProgressBar.id)
        getRecyclerViewItem(PhotosListViewIds.PhotosRecyclerView.id, 0, PhotosListViewIds.ItemImageView.id).click()
    }

    fun navigateBack() {
        val appCompatImageButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                UITestUtils.childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.toolbar),
                        UITestUtils.childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("com.google.android.material.appbar.AppBarLayout")),
                            0
                        )
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageButton.click()
    }
}