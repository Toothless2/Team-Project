package com.group7.unveil.events

interface LandmarkListener
{
    fun updateVisitedCount() { return }
    fun updateVisitedUI(landmarksVisited : Int) { return }
}