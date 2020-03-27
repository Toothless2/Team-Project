package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.group7.unveil.data.Landmarks
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkListener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

/**
 * Tests functionality of calling landmark events
 * <p>
 *     Note: Majority of tests are with a normal unit test @see EventBusTest
 * </p>
 * @author M. Rose
 */
@SmallTest
@RunWith(AndroidJUnit4::class)
class EventBusInstrumentedTest: LandmarkListener {

    lateinit var eventCallSuccessful : Pair<Boolean, Int>

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLandmarkCountTooManyLandmarks()
    {
        val landmarkCount = Landmarks.landmarks.size
        EventBus.callLandmarkUIUpdate(landmarkCount + 1)
    }

    @Test
    fun testLandmarkUIEventCall()
    {
        EventBus.subscribeToLandmarkEvent(this)

        val nMarks = Landmarks.landmarks.size

        EventBus.callLandmarkUIUpdate(nMarks)
        assertTrue(eventCallSuccessful.first)
        assertEquals(eventCallSuccessful.second, nMarks)
    }

    override fun updateVisitedUI(landmarksVisited: Int) {
        eventCallSuccessful = Pair(true, landmarksVisited)
    }
}