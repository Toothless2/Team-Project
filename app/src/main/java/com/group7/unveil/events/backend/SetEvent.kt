package com.group7.unveil.events.backend

class SetEvent<T> private constructor(private val back : MutableSet<(T) -> Unit>) : AbstractEvent<T>(), MutableSet<(T)->Unit> by back{
    constructor() : this(HashSet())
}