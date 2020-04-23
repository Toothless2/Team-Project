@file:Suppress("unused")

package com.group7.unveil.landmarks

import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.util.DistanceHelper

/**
 * Heap for landmark ordering
 * @author M. Rose
 */
object LandmarkHeap {

    private var distanceMax = 50f

    var heap = Landmarks.copyOf().toMutableList()
        private set

    private lateinit var userLoc: LatLng

    private fun parent(pos: Int): Int = pos / 2
    private fun leftChild(pos: Int): Int = pos * 2
    private fun rightChild(pos: Int): Int = (pos * 2) + 1

    private fun isLeaf(pos: Int): Boolean {
        if (pos >= heap.size / 2 && pos <= heap.size)
            return true

        return false
    }

    private fun swap(pos1: Int, pos2: Int) {
        val tmp = heap[pos1]
        heap[pos1] = heap[pos2]
        heap[pos2] = tmp
    }

    private fun minHeapify(pos: Int) {
        if (!isLeaf(pos)) {
            //store the distances to avoid re-calculation as it will be slow
            val posDist = DistanceHelper.getDistance(
                heap[pos].getLatLong(),
                userLoc
            )
            val leftDist = DistanceHelper.getDistance(
                heap[leftChild(
                    pos
                )].getLatLong(),
                userLoc
            )
            val rightDist = DistanceHelper.getDistance(
                heap[rightChild(
                    pos
                )].getLatLong(),
                userLoc
            )

            if (posDist > leftDist || posDist > rightDist) {
                if (leftDist < rightDist) {
                    swap(
                        pos,
                        leftChild(
                            pos
                        )
                    )
                    minHeapify(
                        leftChild(
                            pos
                        )
                    )
                } else {
                    swap(
                        pos,
                        rightChild(
                            pos
                        )
                    )
                    minHeapify(
                        rightChild(
                            pos
                        )
                    )
                }
            }
        }
    }

    /**
     * Add a route into the heap
     */
    fun insert(landmark: Landmark) {
        heap.add(landmark)

        var current = heap.size - 1

        while (DistanceHelper.getDistance(
                heap[current].getLatLong(),
                userLoc
            ) < DistanceHelper.getDistance(
                heap[parent(
                    current
                )].getLatLong(),
                userLoc
            )
        ) {
            swap(
                current,
                parent(current)
            )
            current =
                parent(current)
        }
    }

    /**
     * Outputs the heap if needed
     */
    fun printHeap() {
        for (i in 0 until (heap.size - 1) / 2)
            print("Parent: ${heap[i].name} | Left Child: ${heap[i * 2].name} | Right Child:  ${heap[i * 2 + 1].name}\n")
    }

    /**
     * Create the heap
     * @param userLoc Location of the user so the heap can be made
     */
    fun createMinHeap(userLoc: LatLng) {
        LandmarkHeap.userLoc = userLoc
        for (i in heap.size / 2 downTo 0)
            minHeapify(i)
    }

    /**
     * Remove the root of the heap
     */
    fun removeMin(): Landmark {
        val popped = heap[0]
        heap[0] = heap[heap.size - 1]
        minHeapify(0)
        heap.removeAt(
            heap.size - 1)
        return popped
    }

    /**
     * Peek the root landmark (landmark closest to user)
     */
    fun peekTop(): Landmark {
        return heap[0]
    }

    /**
     * Returns if a landmark can be visited (if it is within range of the user)
     */
    fun landmarkCanBeVisited(): Boolean =
        (::userLoc.isInitialized && DistanceHelper.getDistance(
            peekTop().getLatLong(),
            userLoc
        ) <= distanceMax)
}