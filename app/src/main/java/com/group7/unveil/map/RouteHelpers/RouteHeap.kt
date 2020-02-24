package com.group7.unveil.map.RouteHelpers

import android.util.Size
import com.group7.unveil.map.Landmark
import com.group7.unveil.map.Landmarks

/**
 * Min Heap to store the nearest route to the user
 * @author Max Rose
 */
class RouteHeap {
    companion object {
        private var heap = Landmarks.landmarks.copyOf()

        private fun parent(pos: Int): Int = pos / 2
        private fun leftChild(pos: Int): Int = pos * 2
        private fun rightChild(pos: Int): Int = (pos * 2) + 1

        private fun isLeaf(pos: Int): Boolean {
            if (pos >= heap.size / 2 && pos <= heap.size)
                return true

            return false
        }

        private fun swap(pos1: Int, pos2: Int) {
            var tmp = heap[pos1]
            heap[pos1] = heap[pos2]
            heap[pos2] = tmp
        }
    }
}