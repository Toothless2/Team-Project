package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.landmarks.Landmark
import com.group7.unveil.landmarks.Landmarks
import com.group7.unveil.landmarks.LandmarkHeap
import com.group7.unveil.routes.RouteHeap
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
        LandmarkHeap.createMinHeap(Landmarks[0].getLatLong())
    }

    @Test
    fun testRemoveMin()
    {
        val minVal = LandmarkHeap.heap[0]

        val removedVal = LandmarkHeap.removeMin()

        Assert.assertEquals(minVal, removedVal)
        Assert.assertFalse(LandmarkHeap.heap.contains(minVal))
    }

    @LargeTest
    @Test
    fun testHeapCorrect()
    {
        LandmarkHeap.createMinHeap(testLocation)

        testHeapProperty()
    }

    @Test
    fun testHeapInsert()
    {
        LandmarkHeap.insert(testLandmark)

        // check the item was inserted
        Assert.assertTrue(LandmarkHeap.heap.contains(testLandmark))

        //ensure the heap is still a heap
        testHeapProperty()
    }

    private fun testHeapProperty()
    {
        for (i in 0 until (RouteHeap.heap.size - 1) / 2)
        {
            val parentDist = DistanceHelper.getDistance(testLocation, RouteHeap.heap[i].getStartPos())
            val leftChild = DistanceHelper.getDistance(testLocation, RouteHeap.heap[i*2].getStartPos())
            val rightChild = DistanceHelper.getDistance(testLocation, RouteHeap.heap[i*2+1].getStartPos())

            Assert.assertTrue(parentDist <= leftChild)
            Assert.assertTrue(parentDist < rightChild)
            Assert.assertTrue(rightChild > leftChild)
        }
    }
}