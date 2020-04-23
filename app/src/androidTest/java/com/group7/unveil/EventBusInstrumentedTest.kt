package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.group7.unveil.landmarks.Landmarks
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkEventData
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
class EventBusInstrumentedTest {

    private lateinit var eventCallSuccessful : Pair<Boolean, Int>
    private val landmarkEventHandler : (LandmarkEventData) -> Unit = { updateVisitedUI(it.landmarks) }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLandmarkCountTooManyLandmarks() = EventBus.landmarkEvent(LandmarkEventData(
        Landmarks.size + 1))

    @Test
    fun testLandmarkUIEventCall()
    {
        EventBus.landmarkEvent += landmarkEventHandler

        val nMarks = Landmarks.size

        EventBus.landmarkEvent(LandmarkEventData(nMarks))
        assertTrue(eventCallSuccessful.first)
        assertEquals(eventCallSuccessful.second, nMarks)
        EventBus.landmarkEvent -= landmarkEventHandler
    }

    private fun updateVisitedUI(landmarksVisited: Int) {
        eventCallSuccessful = Pair(true, landmarksVisited)
    }
}