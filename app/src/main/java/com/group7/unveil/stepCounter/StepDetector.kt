package com.group7.unveil.stepCounter

import android.view.VelocityTracker
import kotlin.math.min

class StepDetector {
    private companion object {
        val ACCEL_RING_SIZE = 50
        val VEL_RINGSIZE = 10

        //sensitivity for counting a step
        val STEP_THRESHOLD = 50f

        val STEP_DELAY_NS = 250000000
    }

    private var accelRingCounter = 0
    private var accelRingX = Array(ACCEL_RING_SIZE) { 0f }
    private var accelRingY = Array(ACCEL_RING_SIZE) { 0f }
    private var accelRingZ = Array(ACCEL_RING_SIZE) { 0f }
    private var velRingCounter = 0
    private var velRing = Array(ACCEL_RING_SIZE) { 0f }
    private var lastStepTime: Long = 0
    private var oldVelEstimate = 0f

    private lateinit var stepListener: StepListener

    fun registerListener(listener: StepListener) {
        this.stepListener = listener
    }

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
        worldZ.forEach { x -> x / normilizationFactor }

        val currentZ = SensorFilter.dot(worldZ, currentAccel) - normilizationFactor
        velRingCounter++
        velRing[velRingCounter % VEL_RINGSIZE] = currentZ

        val velEst = velRing.sum()

        if (velEst > STEP_THRESHOLD && oldVelEstimate <= STEP_THRESHOLD && (timeNs - lastStepTime > STEP_DELAY_NS)) {
            stepListener.step(timeNs)
            lastStepTime = timeNs
        }
        oldVelEstimate = velEst
    }
}