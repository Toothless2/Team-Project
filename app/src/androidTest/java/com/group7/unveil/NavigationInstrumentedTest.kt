package com.group7.unveil
//
//import androidx.core.content.contentValuesOf
//import androidx.lifecycle.Lifecycle
//import androidx.test.core.app.ActivityScenario
//import androidx.test.espresso.action.RepeatActionUntilViewState
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.MediumTest
//import com.google.android.gms.maps.model.LatLng
//import com.group7.unveil.data.Landmarks
//import com.group7.unveil.data.StepData
//import com.group7.unveil.events.LandmarkListener
//import com.group7.unveil.stepCounter.LandmarkCounterHeap
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@MediumTest
//@RunWith(AndroidJUnit4::class)
//class NavigationInstrumentedTest : LandmarkListener {
//    var subscriptionResult = -1
//    lateinit var activity : Navigation
//    lateinit var scenerio: ActivityScenario<Navigation>
//
//    @Before
//    fun before()
//    {
//        activity = Navigation()
//
//        scenerio = ActivityScenario.launch(Navigation::class.java)
//        scenerio.moveToState(Lifecycle.State.CREATED)
//    }
//
//    @Test
//    fun testUpdateVisitedCountCloseEnoughAndCanBeVisited()
//    {
//        //setup to increment the counter
//        LandmarkCounterHeap.createMinHeap(Landmarks.landmarks[0].getLatLong())
//        LandmarkCounterHeap.heap[0].visited = false
//
//        //previous values
//        val count = StepData.locationsVisited
//
//        activity.updateVisitedCount()
//
//        Assert.assertTrue(count + 1 == StepData.locationsVisited)
//        Assert.assertTrue(LandmarkCounterHeap.peekTop().visited)
//        Assert.assertTrue(count + 1 == subscriptionResult)
//    }
//
//    @Test
//    fun testUpdateVisitedCountCloseEnoughAndCantBeVisited()
//    {
//        //setup to increment the counter
//        LandmarkCounterHeap.createMinHeap(Landmarks.landmarks[0].getLatLong())
//        LandmarkCounterHeap.heap[0].visited = true
//
//        //previous values
//        val count = StepData.locationsVisited
//
//        activity.updateVisitedCount()
//
//        Assert.assertTrue(count == StepData.locationsVisited)
//        Assert.assertTrue(LandmarkCounterHeap.peekTop().visited)
//    }
//
//    @Test
//    fun testUpdateVisitedCountTooFarAndCanBeVisited()
//    {
//        //setup to increment the counter
//        LandmarkCounterHeap.createMinHeap(LatLng(0.0, 0.0))
//        LandmarkCounterHeap.heap[0].visited = false
//
//        //previous values
//        val count = StepData.locationsVisited
//
//        activity.updateVisitedCount()
//
//        Assert.assertTrue(count == StepData.locationsVisited)
//        Assert.assertFalse(LandmarkCounterHeap.peekTop().visited)
//    }
//
//    @Test
//    fun testUpdateVisitedCountTooFarAndCantBeVisited()
//    {
//        //setup to increment the counter
//        LandmarkCounterHeap.createMinHeap(LatLng(0.0, 0.0))
//        LandmarkCounterHeap.heap[0].visited = true
//
//        //previous values
//        val count = StepData.locationsVisited
//
//        activity.updateVisitedCount()
//
//        Assert.assertTrue(count == StepData.locationsVisited)
//        Assert.assertFalse(LandmarkCounterHeap.peekTop().visited)
//    }
//
//    override fun updateVisitedUI(landmarksVisited : Int) {
//        subscriptionResult = landmarksVisited
//    }
//}