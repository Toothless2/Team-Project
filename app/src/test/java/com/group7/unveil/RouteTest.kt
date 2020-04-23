package com.group7.unveil

import com.group7.unveil.landmarks.Landmark
import com.group7.unveil.routes.Route
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Tests the functionality of the route class
 * @author M. Rose
 */
class RouteTest {
    private lateinit var landmarks:List<Landmark>
    private lateinit var route: Route

    private val routeDescription = "test route name"

    @Before
    fun before()
    {
        landmarks = listOf(
            Landmark(0, "test 1", 70.1, 90.4, "test 1"),
            Landmark(1, "test 2", 100.10, -289.1, "test 2"),
            Landmark(2, "test 3", 81.1, 74.0, "test 3")
        )

        route = Route(landmarks, routeDescription)
    }

    @Test
    fun testRouteDescription()
    {
        Assert.assertEquals(routeDescription, route.description)
    }

    @Test
    fun testRouteName()
    {
        val expectedName = "${landmarks[0].name} -> ${landmarks.last().name} (via ${landmarks[landmarks.size/2].name})"

        Assert.assertEquals(expectedName, route.getName())
    }

    @Test
    fun testShortRouteName()
    {
        val r = Route(listOf(landmarks[0], landmarks[1]), "name")
        val expectedName = "${landmarks[0].name} -> ${landmarks[1].name}"

        Assert.assertEquals(expectedName, r.getName())
    }

    @Test
    fun testGetStartPos()
    {
        Assert.assertEquals(landmarks.first().getLatLong(), route.getStartPos())
    }

    @Test
    fun testGetFirst()
    {
        Assert.assertEquals(landmarks.first(), route.getFirst())
    }

    @Test
    fun testGetLast()
    {
        Assert.assertEquals(landmarks.last(), route.getLast())
    }

    @Test
    fun testGetSize()
    {
        Assert.assertEquals(landmarks.size, route.getSize())
    }
}