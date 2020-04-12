package com.group7.unveil.events.backend

/**
 * @author M. Rose
 */
abstract class AbstractEvent<T> : Event<T> {
    override fun plusAssign(handler: (T) -> Unit) {
        add(handler) // adds the unit to the collection
    }

    override fun minusAssign(handler: (T) -> Unit) {
        remove(handler) // removed the unit from the collection
    }

    override fun invoke(data: T) = forEach { it(data) } // calls foreach in itself passing data to the assigned unit
}