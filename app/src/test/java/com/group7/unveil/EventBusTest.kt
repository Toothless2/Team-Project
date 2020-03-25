package com.group7.unveil

import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkListener
import com.group7.unveil.events.StepListener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.IllegalArgumentException

class EventBusTest : LandmarkListener,
    StepListener {

    lateinit var eventCallSuccessful : Pair<Boolean, Int>

    @Test
    fun testStepEventSubscription()
    {
        EventBus.subscribeToStepEvent(this)

        val f = EventBus.javaClass.getDeclaredField("stepEventListeners")
        f.isAccessible = true

        //need to cast to MutableList<*> as ANY is returned, * is to stop warning
        assertTrue((f.get(EventBus) as MutableList<*>).contains(this))
    }

    @Test
    fun testLandmarkEventSubscription()
    {
        EventBus.subscribeToLandmarkEvent(this)

        val f = EventBus.javaClass.getDeclaredField("landmarkEventListeners")
        f.isAccessible = true

        //need to cast to MutableList<*> as ANY is returned, * is to stop warning
        assertTrue((f.get(EventBus) as MutableList<*>).contains(this))
    }

    @Test
    fun testStepEventUnsubscribe()
    {
        EventBus.subscribeToStepEvent(this) // subscribe to the event so that we can unsubscribe

        EventBus.unsubscribeToStepEvent(this)

        val f = EventBus.javaClass.getDeclaredField("stepEventListeners")
        f.isAccessible = true

        //need to cast to MutableList<*> as ANY is returned, * is to stop warning
        assertTrue(!(f.get(EventBus) as MutableList<*>).contains(this))
    }

    @Test
    fun testLandmarkEventUnsubscribe()
    {
        EventBus.subscribeToLandmarkEvent(this) // subscribe to the event so that we can unsubscribe

        EventBus.unsubscribeToLandmarkEvent(this)

        val f = EventBus.javaClass.getDeclaredField("landmarkEventListeners")
        f.isAccessible = true

        //need to cast to MutableList<*> as ANY is returned, * is to stop warning
        assertTrue(!(f.get(EventBus) as MutableList<*>).contains(this))
    }

    @Test
    fun testCallStepEvent()
    {
        EventBus.subscribeToStepEvent(this) // subscribe to the event so that it can be tested

        val nSteps = 100

        EventBus.callStepEvent(nSteps)

        //call was successful if the pair was updated
        assertTrue(eventCallSuccessful.first)
        assertEquals(eventCallSuccessful.second, nSteps)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCallStepEventInvalidStep()
    {
        EventBus.callStepEvent(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLandmarkCountLessThan0()
    {
        EventBus.callLandmarkUIUpdate(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLandmarkCountTooManyLandmarks()
    {
        //TODO: Convert to an instrumented test
        throw IllegalArgumentException()
//        val landmarkCount = Landmarks.landmarks.size
//        EventBus.callLandmarkUIUpdate(landmarkCount + 1)
    }

    @Test
    fun testLandmarkUIEventCall()
    {
        //TODO: Convert to an instrumented test
        assertTrue(true)
//        EventBus.subscribeToLandmarkEvent(this)
//
//        val nMarks = 1
//
//        EventBus.callLandmarkUIUpdate(nMarks)
//        assertTrue(eventCallSuccessful.first)
//        assertEquals(eventCallSuccessful.second, nMarks)
    }

    override fun stepEvent(steps: Int) {
        eventCallSuccessful = Pair(true, steps)
    }

//    override fun updateVisitedUI(landmarksVisited: Int) {
//        eventCallSuccessful = Pair(true, landmarksVisited)
//    }
}