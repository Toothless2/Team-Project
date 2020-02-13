package com.group7.unveil.map.RouteHelpers

/**
 * Callback used for when URL data has been retrieved
 * @author Max Rose
 */
interface TaskLoadedCallback {
    fun onTaskDone(vararg values: Any)
}