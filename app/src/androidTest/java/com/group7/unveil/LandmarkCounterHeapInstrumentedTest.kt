package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.data.Landmark
import com.group7.unveil.data.Landmarks
import com.group7.unveil.data.Route
import com.group7.unveil.map.routeHelpers.RouteHeap
import com.group7.unveil.stepCounter.LandmarkCounterHeap
import com.group7.unveil.util.DistanceHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests the functionality of the LandmarkCounterHeap
 * @author M. Rose
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class LandmarkCounterHeapInstrumentedTest {
    private val testLocation = LatLng(0.0, 0.0)
    private val testLandmark = Landmark(0, "test 1", 70.1, 90.4, "test")

    @Before
    fun before()
    {
        LandmarkCounterHeap.createMinHeap(Landmarks.landmarks[0].getLatLong())
    }

    @Test
    fun testRemoveMin()
    {
        val minVal = LandmarkCounterHeap.heap[0]

        val removedVal = LandmarkCounterHeap.removeMin()

        Assert.assertEquals(minVal, removedVal)
        Assert.assertFalse(LandmarkCounterHeap.heap.contains(minVal))
    }

    @LargeTest
    @Test
    fun testHeapCorrect()
    {
        LandmarkCounterHeap.createMinHeap(testLocation)

        testHeapProperty()
    }

    @Test
    fun testHeapInsert()
    {
        LandmarkCounterHeap.insert(testLandmark)

        // check the item was inserted
        Assert.assertTrue(LandmarkCounterHeap.heap.contains(testLandmark))

        //ensure the heap is still a heap
        testHeapProperty()
    }

    private fun testHeapProperty()
    {
        for (i in 0 until (RouteHeap.heap.size - 1) / 2)
        {
            val parentDist = DistanceHelper.getDistace(testLocation, RouteHeap.heap[i].getStartPos())
            val leftChild = DistanceHelper.getDistace(testLocation, RouteHeap.heap[i*2].getStartPos())
            val rightChild = DistanceHelper.getDistace(testLocation, RouteHeap.heap[i*2+1].getStartPos())

            Assert.assertTrue(parentDist <= leftChild)
            Assert.assertTrue(parentDist < rightChild)
            Assert.assertTrue(rightChild > leftChild)
        }
    }
}