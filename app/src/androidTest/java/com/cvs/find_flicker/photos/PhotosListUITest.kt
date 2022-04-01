package com.cvs.find_flicker.photos

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.cvs.find_flicker.photo_detail.PhotoDetailViewIds
import com.cvs.find_flicker.ui.MainActivity
import com.cvs.find_flicker.utils.*
import com.cvs.find_flicker.utils.NavigationHelper.openPhotoDetail
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4ClassRunner::class)
class PhotosListUITest {

    @get:Rule
    var mActivityTestRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var photosRecyclerView: ViewInteraction
    private lateinit var searchView: ViewInteraction
    @Before
    fun intentsInit() {
        Intents.init()
        photosRecyclerView = Espresso.onView((withId(PhotosListViewIds.PhotosRecyclerView.id)))
        searchView = Espresso.onView((withId(PhotosListViewIds.SearchView.id)))
    }

    @After
    fun intentsTeardown() {
        // release Espresso Intents capturing
        Intents.release()
    }
    @Test
    fun checkExistence() {
        photosRecyclerView.checkExistence()
        searchView.checkExistence()
    }

    @Test
    fun checkPhotoDisplayed() {
        UITestUtils.waitForViewDisappear(PhotosListViewIds.ProgressBar.id)
        getRecyclerViewItem(PhotosListViewIds.PhotosRecyclerView.id, 0, PhotosListViewIds.ItemTitle.id).checkExistence()
    }

    @Test
    fun navigateToDetail() {
        openPhotoDetail()
        Espresso.onView((withId(PhotoDetailViewIds.Author.id))).checkExistence()
    }
}