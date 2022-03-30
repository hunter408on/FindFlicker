package com.cvs.find_flicker.photos

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.cvs.find_flicker.photo_detail.PhotoDetailViewIds
import com.cvs.find_flicker.ui.MainActivity
import com.cvs.find_flicker.utils.checkExistence
import com.cvs.find_flicker.utils.click
import com.cvs.find_flicker.utils.getRecyclerViewItem
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class PhotosListTest {

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
        Thread.sleep(3000)
        getRecyclerViewItem(PhotosListViewIds.PhotosRecyclerView.id, 0, PhotosListViewIds.ItemTitle.id).checkExistence()
    }

    @Test
    fun navigateToDetail() {
//        Thread.sleep(3000)
//        getRecyclerViewItem(PhotosListViewIds.PhotosRecyclerView.id, 0, PhotosListViewIds.ItemTitle.id).click()
//        Espresso.onView((withId(PhotoDetailViewIds.Author.id))).checkExistence()
    }
}