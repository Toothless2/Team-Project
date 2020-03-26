package com.group7.unveil

import com.group7.unveil.events.EventBus
import com.group7.unveil.events.LandmarkListener
import com.group7.unveil.events.StepListener
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
class EventBusTest : LandmarkListener, StepListener {
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
    fun testStepDuplicateEventSubscription()
    {
        EventBus.subscribeToStepEvent(this)
        EventBus.subscribeToStepEvent(this)

        val f = EventBus.javaClass.getDeclaredField("stepEventListeners")
        f.isAccessible = true

        var counter = 0

        for(l in f.get(EventBus) as MutableList<*>)
            if(l == this)
                counter++

        assertEquals(1, counter)
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
    fun testLandmarkDuplicateEventSubscription()
    {
        EventBus.subscribeToLandmarkEvent(this)
        EventBus.subscribeToLandmarkEvent(this)

        val f = EventBus.javaClass.getDeclaredField("landmarkEventListeners")
        f.isAccessible = true

        var counter = 0

        for(l in f.get(EventBus) as MutableList<*>)
            if(l == this)
                counter++

        assertEquals(1, counter)
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

    override fun stepEvent(steps: Int) {
        eventCallSuccessful = Pair(true, steps)
    }
}