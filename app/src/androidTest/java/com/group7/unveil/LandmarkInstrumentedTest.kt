package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.landmarks.Landmarks
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests functionality of Landmarks class
 * @author M. Rose
 */
@SmallTest
@RunWith(AndroidJUnit4::class)
class LandmarkInstrumentedTest {
    @Test
    fun testCorrectCentre()
    {
        Assert.assertEquals(LatLng(54.9733026, -1.6249138), Landmarks.centre)
    }

    @Test
    fun testLandmarkArraySize()
    {
        Assert.assertTrue(Landmarks.size > 0)
    }
}