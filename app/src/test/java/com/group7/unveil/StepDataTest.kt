package com.group7.unveil

import com.group7.unveil.data.StepData
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.math.round

/**
 * Tests functionality of the the StepData class
 * @author M. Rose
 */
class StepDataTest {
    val stepCount = 100

    @Before
    fun before()
    {
        StepData.steps = stepCount
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSetStepsError()
    {
        StepData.steps = -1
    }

    @Test
    fun testSetSteps()
    {
        StepData.steps = stepCount

        Assert.assertEquals(stepCount, StepData.steps)
    }

    @Test
    fun testGetDistance()
    {
        val expectedDistance = round((stepCount * 0.00045) * 100.0) / 100.0

        Assert.assertEquals(expectedDistance, StepData.getDistance(), 0.001)
    }

    @Test
    fun testGetDistanceWithUnit()
    {
        val expectedString = "${round((stepCount * 0.00045) * 100.0) / 100.0} miles"

        Assert.assertEquals(expectedString, StepData.getDistanceWithUnit())
    }
}