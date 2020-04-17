package com.group7.unveil

import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.landmarks.Landmark
import org.junit.Assert
import org.junit.Test

/**
 * Tests Functionality of Landmark class
 * @author M. Rose
 */
class LandmarkTest {
    @Test
    fun testGetLatLong()
    {
        val mark = Landmark(0, "test", 1.0, 2.0, "test")

        Assert.assertEquals(mark.getLatLong(), LatLng(1.0, 2.0))
    }
}