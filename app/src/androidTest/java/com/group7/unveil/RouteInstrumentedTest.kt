package com.group7.unveil

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.group7.unveil.routes.Routes
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests functionality of the Routes class
 * @author M. Rose
 */
@SmallTest
@RunWith(AndroidJUnit4::class)
class RouteInstrumentedTest {
    @Test
    fun testRouteCreated()
    {
        Assert.assertTrue(Routes.size > 0)
    }
}