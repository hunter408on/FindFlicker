package com.cvs.find_flicker.photo_detail

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.cvs.find_flicker.photos.PhotosListViewIds
import com.cvs.find_flicker.ui.MainActivity
import com.cvs.find_flicker.utils.NavigationHelper.navigateBack
import com.cvs.find_flicker.utils.NavigationHelper.openPhotoDetail
import com.cvs.find_flicker.utils.checkExistence
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
class PhotoDetailUITest {

    @get:Rule
    var mActivityTestRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var photoImageView: ViewInteraction
    private lateinit var photoTitle: ViewInteraction
    private lateinit var photoWidth: ViewInteraction
    private lateinit var photoHeight: ViewInteraction
    private lateinit var photoAuthor: ViewInteraction
    private lateinit var photoDesc: ViewInteraction
    @Before
    fun intentsInit() {
        Intents.init()
        openPhotoDetail()
        photoImageView = Espresso.onView((withId(PhotoDetailViewIds.ImageView.id)))
        photoTitle = Espresso.onView((withId(PhotoDetailViewIds.Title.id)))
        photoWidth = Espresso.onView((withId(PhotoDetailViewIds.Width.id)))
        photoHeight = Espresso.onView((withId(PhotoDetailViewIds.Height.id)))
        photoAuthor = Espresso.onView((withId(PhotoDetailViewIds.Author.id)))
        photoDesc = Espresso.onView((withId(PhotoDetailViewIds.Description.id)))

    }

    @After
    fun intentsTeardown() {
        // release Espresso Intents capturing
        Intents.release()
    }
    @Test
    fun checkExistence() {
        photoImageView.checkExistence()
        photoTitle.checkExistence()
        photoWidth.checkExistence()
        photoHeight.checkExistence()
        photoAuthor.checkExistence()
        photoDesc.checkExistence()
    }


    @Test
    fun testGoBack() {
        navigateBack()
        Espresso.onView((withId(PhotosListViewIds.PhotosRecyclerView.id))).checkExistence()
    }
}