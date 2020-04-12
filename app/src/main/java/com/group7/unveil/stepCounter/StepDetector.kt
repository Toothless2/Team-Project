package com.group7.unveil.stepCounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.group7.unveil.data.StepData
import com.group7.unveil.events.EventBus
import com.group7.unveil.events.StepEventData
import kotlin.math.min

/**
 * Detects steps by reading values from the accelerometer
 * @author Adapted from a GitHub repo that I cant find anymore (M. Rose)
 */
class StepDetector : SensorEventListener {
    private companion object {
        const val ACCEL_RING_SIZE = 50
        const val VEL_RINGSIZE = 10

        //sensitivity for counting a step
        const val STEP_THRESHOLD = 200f

        const val STEP_DELAY_NS = 250000000
    }

    private var accelRingCounter = 0
    private var accelRingX = Array(ACCEL_RING_SIZE) { 0f }
    private var accelRingY = Array(ACCEL_RING_SIZE) { 0f }
    private var accelRingZ = Array(ACCEL_RING_SIZE) { 0f }
    private var velRingCounter = 0
    private var velRing = Array(ACCEL_RING_SIZE) { 0f }
    private var lastStepTime: Long = 0
    private var oldVelEstimate = 0f

    fun updateAccel(timeNs: Long, x: Float, y: Float, z: Float) {
        val currentAccel = arrayOf(x, y, z)

        //update velocity guess
        accelRingCounter++
        accelRingX[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[0]
        accelRingY[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[1]
        accelRingZ[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[2]

        val worldZ = Array(3) { 0f }
        worldZ[0] = accelRingX.sum() / min(accelRingCounter, ACCEL_RING_SIZE)
        worldZ[0] = accelRingY.sum() / min(accelRingCounter, ACCEL_RING_SIZE)
        worldZ[0] = accelRingZ.sum() / min(accelRingCounter, ACCEL_RING_SIZE)

        val normilizationFactor = SensorFilter.norm(worldZ)
        worldZ.forEach { it / normilizationFactor }

        val currentZ = SensorFilter.dot(worldZ, currentAccel) - normilizationFactor
        velRingCounter++
        velRing[velRingCounter % VEL_RINGSIZE] = currentZ

        val velEst = velRing.sum()

        if (velEst > STEP_THRESHOLD && oldVelEstimate <= STEP_THRESHOLD && (timeNs - lastStepTime > STEP_DELAY_NS)) {
            EventBus.stepEvent(StepEventData(++StepData.steps))
//            EventBus.callStepEvent(++StepData.steps)
            lastStepTime = timeNs
        }
        oldVelEstimate = velEst
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            updateAccel(
                event.timestamp,
                event.values[0],
                event.values[1],
                event.values[2]
            )
        }
    }
}