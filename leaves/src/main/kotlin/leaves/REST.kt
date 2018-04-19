package leaves

import dto.*
import org.w3c.xhr.XMLHttpRequest

typealias Callback<T> = (result: T) -> Unit


abstract class REST(url:String) {

    val serverNoSlash = if (url.endsWith("/")) url.slice(0..url.length-2) else url

    /**
     * Makes a leaves.REST call that expects a JSON response in the callback
     */
    fun <T> call(method: String, service: String, callback: (result: T) -> Unit, params: String? = null, json: Any? = null) {
        call(service, method, params, json, { callback(JSON.parse(it.response as String))})
    }

    /**
     * Makes a leaves.REST call that does not expect a JSON response in the callback
     */
    fun call(method: String, service: String, callback: () -> Unit, params: String? = null, json: Any? = null) {
        call(service, method, params, json, { callback() })
    }

    private fun makeXhr(parseAndCallback: (xhr: XMLHttpRequest) -> Unit): XMLHttpRequest {
        val xhr = XMLHttpRequest()
        xhr.onreadystatechange = {

            if (xhr.readyState == XMLHttpRequest.DONE)
                if (xhr.status == 200.toShort())
                parseAndCallback(xhr)
                else
                    throw Exception(xhr.toString())
        }

        return xhr
    }

    private fun call(service: String, method: String, params: String?, json: Any?, parseAndCallback: (xhr: XMLHttpRequest)->Unit) {
        val xhr = makeXhr(parseAndCallback)
        val url = "$serverNoSlash/$service" + (getUrlParams() ?: "")

        console.log("getting $url")
        xhr.open(method, url)
        if (params != null) {

            if (json != null)
                throw Exception("Specify params or json, not both")

            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
            xhr.send(params)

        } else if (json != null) {

            xhr.setRequestHeader("Content-type", "application/json")
            xhr.send(json)

        } else {

            xhr.send()

        }
    }

    abstract fun getUrlParams(): String?
}


class TeaREST(url:String): REST(url) {

    var sessionId: String? = null

    override fun getUrlParams() = if (sessionId != null) "?sessionId=$sessionId" else ""

    fun getPrices(callback: Callback<Prices>) = call("GET", "cc", callback)
}



