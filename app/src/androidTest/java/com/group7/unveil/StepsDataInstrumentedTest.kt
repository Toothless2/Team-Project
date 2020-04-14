package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.group7.unveil.data.Landmarks
import com.group7.unveil.data.LocationData
import com.group7.unveil.data.StepData
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

/**
 * Tests landmark functionality of StepsData class
 * @author M. Rose
 */
@SmallTest
@RunWith(AndroidJUnit4::class)
class StepsDataInstrumentedTest {
    @Test
    fun testValidLocationValue()
    {
        val locVal = Landmarks.landmarks.size
        LocationData.locationsVisited = locVal
        Assert.assertEquals(locVal, LocationData.locationsVisited)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLocationValueTooSmall()
    {
        LocationData.locationsVisited = -1
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidLocationValueTooLarge()
    {
        LocationData.locationsVisited = Landmarks.landmarks.size + 1
    }
}