package org.w3c.dom.events

public external open class EventWithOriginalEvent<T>(): Event {
    open val originalEvent: T
}