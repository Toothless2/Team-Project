package com.group7.unveil

import com.group7.unveil.events.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * Tests most functionality of the event bus
 * <p>
 *     Note: Does not test some functionality as requires an instrumented test @see EventBusInstrumentedTest
 * </p>
 * @author M. Rose
 */
class EventBusTest {
    private lateinit var eventCallSuccessful : Pair<Boolean, Int>

    private val stepEventHandler : (StepEventData) -> Unit = { stepEvent(it.steps) }
    private val landmarkEventHandler : (LandmarkEventData) -> Unit = {  }

    @Test
    fun testStepEventSubscription()
    {
        EventBus.stepEvent.clear() // clear the event before

        EventBus.stepEvent += stepEventHandler

        //need to cast to MutableList<*> as ANY is returned, * is to stop warning
        assertEquals(1, EventBus.stepEvent.size)
    }

    @Test
    fun testStepDuplicateEventSubscription()
    {
        EventBus.stepEvent.clear() // clear the event before

        EventBus.stepEvent += stepEventHandler
        EventBus.stepEvent += stepEventHandler

        assertEquals(1, EventBus.stepEvent.size)
    }

    @Test
    fun testLandmarkEventSubscription()
    {
        EventBus.landmarkEvent.clear()
        EventBus.landmarkEvent += landmarkEventHandler

        assertEquals(1, EventBus.landmarkEvent.size)
    }

    @Test
    fun testLandmarkDuplicateEventSubscription()
    {
        EventBus.landmarkEvent.clear()
        EventBus.landmarkEvent += landmarkEventHandler
        EventBus.landmarkEvent += landmarkEventHandler

        assertEquals(1, EventBus.landmarkEvent.size)
    }

    @Test
    fun testStepEventUnsubscribe()
    {
        EventBus.stepEvent.clear() // clear the event before

        EventBus.stepEvent += stepEventHandler // subscribe to the event so that we can unsubscribe

        EventBus.stepEvent -= stepEventHandler

        assertEquals(0, EventBus.stepEvent.size)
    }

    @Test
    fun testLandmarkEventUnsubscribe()
    {
        EventBus.landmarkEvent.clear()
        EventBus.landmarkEvent += landmarkEventHandler // subscribe to the event so that we can unsubscribe

        EventBus.landmarkEvent -= landmarkEventHandler

        assertEquals(0, EventBus.landmarkEvent.size)
    }

    @Test
    fun testCallStepEvent()
    {
        EventBus.stepEvent.clear() // clear the event before

        EventBus.stepEvent += stepEventHandler // subscribe to the event so that it can be tested

        val nSteps = 100

        EventBus.stepEvent(StepEventData(nSteps))

        //call was successful if the pair was updated
        assertTrue(eventCallSuccessful.first)
        assertEquals(eventCallSuccessful.second, nSteps)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCallStepEventInvalidStep()= EventBus.stepEvent(StepEventData(-1))

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLandmarkCountLessThan0() = EventBus.landmarkEvent(LandmarkEventData(-1))

    private fun stepEvent(steps: Int) {
        eventCallSuccessful = Pair(true, steps)
    }
}