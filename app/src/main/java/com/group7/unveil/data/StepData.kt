package com.group7.unveil.data

import kotlin.math.round

class StepData {
    companion object {
        var steps = 0

        //assuming walking pace of 3miles/h
        fun getDistance() = round((steps * 0.00045) * 100.0) / 100.0
    }
}