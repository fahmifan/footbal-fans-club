package io.github.miun173.footballfans.main

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.github.miun173.footballfans.R
import io.github.miun173.footballfans.repository.local.DBContract
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            ApplicationProvider.getApplicationContext<Context>().deleteDatabase(DBContract.DB_NAME)
        }
    }


    @Test
    fun testBottomNav() {
        // check if there is a bottom navbar
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))

        // then, click on the `past match` button
        onView(withId(R.id.nav_match)).perform(click())
                .check(matches(isDisplayed()))

        // wait for 5sec
        Thread.sleep(5000)

        // check, if there is text `Everton` then click on it. We should on detail view
        onView(withText("Arsenal")).perform(click())

        // check if there is a `Goal` text displayed there
        onView(withText("Goal")).check(matches(isDisplayed()))

        // go back to main view
        pressBack()

        // check if there is text `Wolves`. Then clicked it, we should on detail view
        onView(withText("Wolves")).check(matches(isDisplayed()))
                .perform(click())

        // click `Love` button, it will add match to favorite list
        onView(withId(R.id.menu_add_fav)).perform(click())

        // wait 5sec when add match to favorite
        Thread.sleep(5000)

        // back to main view
        pressBack()

        // click on `Favorite` button
        onView(withId(R.id.nav_favmatch)).perform(click())

        // wait 5sec to load favorite match(es)
        Thread.sleep(5000)

        // check if the layout is there
        onView(withId(R.id.tv_team_home)).check(matches(isDisplayed()))
    }
}