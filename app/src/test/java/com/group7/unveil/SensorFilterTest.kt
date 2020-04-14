package com.group7.unveil

import com.group7.unveil.stepCounter.SensorFilter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.lang.IllegalArgumentException
import kotlin.math.sqrt

/**
 * Tests functionality of the SensorFilter class
 * @author M. Rose
 */
@RunWith(Enclosed::class)
class SensorFilterTest() {

    @RunWith(Parameterized::class)
    class SensorFilterParameterized(private val vec: Array<Float>)
    {
        companion object {
            @JvmStatic
            @Parameterized.Parameters(name = "Test {index}")
            fun data() = arrayOf(
                arrayOf(arrayOf(-1f, 12f, 89f)), // mess but only wat to properly pass the array as the parameter
                arrayOf(arrayOf(-20f, 8f, 2f)),
                arrayOf(arrayOf(-1f, 12f))
            )
        }

        @Test
        fun testNorm()
        {
            var ret = 0f

            for (v in vec)
                ret += v * v

            Assert.assertEquals(sqrt(ret), SensorFilter.norm(vec), 0.1f)
        }
    }

    class SensorFilterNonParameterized // don't need to run these multiple times so might as well split it
    {
        private lateinit var vectors : Array<Array<Float>>

        @Before
        fun before()
        {
            vectors = arrayOf(
                arrayOf(-1f, 12f, 89f),
                arrayOf(-20f, 8f, 2f),
                arrayOf(-1f, 12f)
            )
        }

        @Test
        fun testDot()
        {
            var expected = 0f

            for(i in vectors.indices)
                expected += vectors[0][i] * vectors[1][i]

            Assert.assertEquals(expected, SensorFilter.dot(vectors[0], vectors[1]))
        }

        @Test(expected = IllegalArgumentException::class)
        fun testDotError()
        {
            SensorFilter.dot(vectors.first(), vectors.last())
        }
    }
}