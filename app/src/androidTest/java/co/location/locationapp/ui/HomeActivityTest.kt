package co.location.locationapp.ui

import android.content.Context
import android.content.Intent
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.location.locationapp.R
import co.location.locationapp.ui.home.HomeActivity
import co.location.locationapp.util.BaseTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class HomeActivityTest : BaseTest() {

    override fun isMockServerEnabled(): Boolean = true

    @get:Rule
    var testRule = CountingTaskExecutorRule()


    @Test
    fun loaderTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext<Context>(), HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        testRule.drainTasks(10, TimeUnit.SECONDS)
        Espresso.onView(withId(R.id.progressBar)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun resultTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext<Context>(), HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        testRule.drainTasks(10, TimeUnit.SECONDS)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
        waitForAdapterChange(recyclerView)
        Assert.assertNotNull(recyclerView.adapter)
        waitForAdapterChange(recyclerView)
        assertThat(recyclerView.adapter!!.itemCount > 0, `is`<Boolean>(true))
        Espresso.onView(withId(R.id.progressBar)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun clickOnItemTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext<Context>(), HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        testRule.drainTasks(10, TimeUnit.SECONDS)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Espresso.onView(withId(R.id.recyclerView))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.`is`(activity.window.decorView)))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        pressBack()
    }


    @Test
    fun scrollAndClickTest() {
        val intent = Intent(ApplicationProvider.getApplicationContext<Context>(), HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent)
        testRule.drainTasks(10, TimeUnit.SECONDS)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Espresso.onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(10))
                .perform(click())
        pressBack()
    }


    private fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter!!.registerAdapterDataObserver(
                    object : RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            latch.countDown()
                        }

                        override fun onChanged() {
                            latch.countDown()
                        }
                    })
        }
        if (recyclerView.adapter!!.itemCount > 0) {
            return //already loaded
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), `is`<Boolean>(true))
    }

}
