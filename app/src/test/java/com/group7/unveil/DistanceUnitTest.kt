package com.group7.unveil

import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.util.DistanceHelper
import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * Tests functionality of the Distance function
 * @author M. Rose
 */
class DistanceUnitTest {
    @Test
    fun testDistanceToSamePointIsZero()
    {
        val point = LatLng(100.0, 100.0)
        assertEquals(DistanceHelper.getDistace(point, point), 0.0, 0.01)
    }

    @Test
    fun testPointDistanceOrder()
    {
        val p1 = LatLng(10.0, -25.0)
        val p2 = LatLng(9.0, 7.57)

        val d1 = DistanceHelper.getDistace(p1, p2)
        val d2 = DistanceHelper.getDistace(p2, p1)

        assertEquals(d1, d2, 0.0001)
    }
}