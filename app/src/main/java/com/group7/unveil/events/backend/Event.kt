package com.group7.unveil.events.backend

/**
 *
 * @author M. Rose
 */
interface Event<T> : MutableCollection<(T) -> Unit> {
    operator fun plusAssign(handler : (T) -> Unit) // operator to assign to an event (passes a data object if type T to the assigned Unit (method))
    operator fun minusAssign(handler: (T) -> Unit)
    operator fun invoke(data : T) // passes the data to the units assigned to the event
}