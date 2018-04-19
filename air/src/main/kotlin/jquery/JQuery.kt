@file:Suppress("unused")
package jquery

import org.w3c.dom.Element
import org.w3c.dom.events.Event

//The Kotlin-supplied version of this is deprecated, so this is a copy/paste of that w/o the deprecation warnings

external class JQuery() {
    fun addClass(className: String): JQuery
    fun addClass(f: (Int, String) -> String): JQuery

    fun attr(attrName: String): String
    fun attr(attrName: String, value: String): JQuery

    fun html(): String
    fun html(s: String): JQuery
    fun html(f: (Int, String) -> String): JQuery

    fun find(s: String): JQuery
    fun remove()

    fun hasClass(className: String): Boolean
    fun removeClass(className: String): JQuery
    fun height(): Number
    fun width(): Number

    fun click(): JQuery

    fun mousedown(handler: (MouseEvent) -> Unit): JQuery
    fun mouseup(handler: (MouseEvent) -> Unit): JQuery
    fun mousemove(handler: (MouseEvent) -> Unit): JQuery

    fun dblclick(handler: (MouseClickEvent) -> Unit): JQuery
    fun click(handler: (MouseClickEvent) -> Unit): JQuery

    fun load(handler: () -> Unit): JQuery
    fun change(handler: () -> Unit): JQuery

    fun css(key: String, value: String): JQuery
    fun clone(): JQuery
    fun append(any: Any): JQuery
    fun ready(handler: () -> Unit): JQuery
    fun text(text: String): JQuery
    fun slideUp(): JQuery
    fun hover(handlerInOut: () -> Unit): JQuery
    fun hover(handlerIn: () -> Unit, handlerOut: () -> Unit): JQuery
    fun next(): JQuery
    fun parent(): JQuery
    @JsName("val")
    fun value(): String?
    fun off(): JQuery
    fun prepend(value: JQuery): JQuery
    fun data(key: String): Any
    fun data(key: String, value: Any): Unit
    fun on(s: String, function: (Event, Data) -> Unit): JQuery
    @JsName("on")
    fun onBoolean(s: String, function: (Event, Data) -> Boolean): JQuery
    var length: Int
    fun <T> bind(s: String, function: (T) -> Unit)
    fun each(function: (Int, String) -> Unit)
    @JsName("is")
    fun IS(s: String): Boolean
    fun pivotUI(vararg things: Any)
    fun pivot(vararg things: Any)
    val pivotUtilities: PivotUtilities
}

external class PivotUtilities() {
    val SubtotalPivotData: Any
    val derivers: Any
    val subtotal_renderers: Any
}

/** If this JQuery thing is actually the result of finding nothing, return the result of f() instead */
fun JQuery.notFound(f: ()->JQuery): JQuery = if (this.notFound()) f() else this

/** return true if this JQuery object matched nothing */
fun JQuery.notFound() = this.length == 0

external class Data {
    val toPage: JQuery
}

open external class MouseEvent() {
    val pageX: Double
    val pageY: Double
    fun preventDefault()
    fun isDefaultPrevented(): Boolean
}

external class MouseClickEvent : MouseEvent {
    val which: Int
}

@JsName("$")
external fun jq(selector: String): JQuery
@JsName("$")
external fun jq(selector: String, context: Element): JQuery
@JsName("$")
external fun jq(callback: () -> Unit): JQuery
@JsName("$")
external fun jq(obj: JQuery): JQuery
@JsName("$")
external fun jq(el: Element): JQuery
@JsName("$")
external fun jq(): JQuery
@JsName("$")
external val dollar: JQuery
