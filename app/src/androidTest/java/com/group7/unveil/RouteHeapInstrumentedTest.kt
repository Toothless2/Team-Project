package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.data.Landmark
import com.group7.unveil.data.Route
import com.group7.unveil.routes.RouteHeap
import com.group7.unveil.util.DistanceHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests functionality of the RouteHeap
 * @author M. Rose
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class RouteHeapInstrumentedTest {
    private val testLocation = LatLng(0.0, 0.0)

    lateinit var testRoute: Route

    @Before
    fun before()
    {
        RouteHeap.createMinHeap(testLocation)


        val landmarks = listOf(
            Landmark(0, "test 1", 70.1, 90.4, "test 1"),
            Landmark(1, "test 2", 100.10, -289.1, "test 2"),
            Landmark(2, "test 3", 81.1, 74.0, "test 3")
        )

        testRoute = Route(landmarks, "description")
    }

    @Test
    fun testRemoveMin()
    {
        val minVal = RouteHeap.heap[0]

        val removedVal = RouteHeap.removeMin()

        Assert.assertEquals(minVal, removedVal)
        Assert.assertFalse(RouteHeap.heap.contains(minVal))
    }

    @LargeTest
    @Test
    fun testHeapCorrect()
    {
        RouteHeap.createMinHeap(testLocation)

        testHeapProperty()
    }

    @Test
    fun testHeapInsert()
    {
        RouteHeap.insert(testRoute)

        // check the item was inserted
        Assert.assertTrue(RouteHeap.heap.contains(testRoute))

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