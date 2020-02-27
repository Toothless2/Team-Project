package com.group7.unveil.map.RouteHelpers

import android.util.Size
import com.google.android.gms.maps.model.LatLng
import com.group7.unveil.map.Landmark
import com.group7.unveil.map.Landmarks
import com.group7.unveil.map.Route
import com.group7.unveil.map.Routes
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Min Heap to store the nearest route to the user
 * @author Max Rose
 */
class RouteHeap {
    companion object {
        private var heap = Routes.routes.copyOf().toMutableList()
        lateinit var userLoc: LatLng

        private fun parent(pos: Int): Int = pos / 2
        private fun leftChild(pos: Int): Int = pos * 2
        private fun rightChild(pos: Int): Int = (pos * 2) + 1

        fun getHeap(): List<Route> = heap

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
                val posDist = distToMe(heap[pos].landmarks[0].getLatLong())
                val leftDist = distToMe(heap[leftChild(pos)].landmarks[0].getLatLong())
                val rightDist = distToMe(heap[rightChild(pos)].landmarks[0].getLatLong())

                if (posDist > leftDist || posDist > rightDist) {
                    if (leftDist < rightDist) {
                        swap(pos, leftChild(pos))
                        minHeapify(leftChild(pos))
                    } else {
                        swap(pos, rightChild(pos))
                        minHeapify(rightChild(pos))
                    }
                }
            }
        }

        /**
         * Add a route into the heap
         */
        fun insert(route: Route) {
            heap.add(route)

            var current = heap.size - 1

            while (distToMe(heap[current].landmarks[0].getLatLong()) < distToMe(heap[parent(current)].landmarks[0].getLatLong())) {
                swap(current, parent(current))
                current = parent(current)
            }
        }

        /**
         * Outputs the heap incase needed
         */
        fun printHeap() {
            for (i in 0 until (heap.size - 1) / 2)
                print(
                    "Parent: ${Routes.routeName(heap[i])} | Left Child: ${Routes.routeName(heap[i * 2])} | Right Child:  ${Routes.routeName(
                        heap[i * 2 + 1]
                    )}\n"
                )
        }

        /**
         * Create the heap
         * @param userLoc Location of the user so the heap can be made
         */
        fun createMinHeap(userLoc: LatLng) {
            this.userLoc = userLoc
            for (i in heap.size / 2 downTo 0)
                minHeapify(i)
        }

        /**
         * Remove the root of the heap
         */
        fun removeMin(): Route {
            val poppped = heap[0]
            heap[0] = heap[heap.size - 1]
            minHeapify(0)
            heap.removeAt(heap.size - 1)
            return poppped
        }

        /**
         * Gets the distance between a given point and the user
         * @author Adapted from https://www.movable-type.co.uk/scripts/latlong.html
         */
        private fun distToMe(pos: LatLng): Double {
            val r = 6371e3
            val l1 = Math.toRadians(pos.latitude)
            val l2 = Math.toRadians(userLoc.latitude)
            val deltaRoh = Math.toRadians(userLoc.latitude - pos.latitude)
            val deltaLambda = Math.toRadians(userLoc.longitude - pos.longitude)

            val a = sin(deltaRoh / 2) * sin(deltaRoh) +
                    cos(l1) * cos(l2) *
                    sin(deltaLambda / 2) * sin(deltaLambda / 2)

            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            return r * c
        }
    }
}