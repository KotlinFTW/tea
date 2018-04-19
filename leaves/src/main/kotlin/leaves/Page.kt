package leaves


import dto.Prices
import jquery.jq
import kotlin.browser.window

fun Double.round() = this.asDynamic().toFixed(2)

fun formatPriceDiff(selector: String, price: Double) {

    var elem = jq(selector)
    val roundedPrice = price.round()
    val currentPrice = elem.data("price")
    if (currentPrice != roundedPrice) {
        elem.data("price", roundedPrice)
        elem.text(roundedPrice.toString())
        if (roundedPrice > 0) {
            elem.removeClass("red").addClass("green")
        }
        else {
            elem.removeClass("green").addClass("red")
        }
        elem.removeClass("flashgreen").removeClass("flashred")

        //setTimeout is necessary because the animation won't restart unless we let javascript figure out it went away and came back.
        if (roundedPrice > currentPrice) {
            window.setTimeout({ elem.addClass("flashgreen") })
        }
        else if (roundedPrice < currentPrice) {
            window.setTimeout({ elem.addClass("flashred") })
        }
    }
}

fun main(args: Array<String>) {
    val rest = TeaREST("")

    jq {
        var callback: Callback<Prices>? = null
        callback = {
            it.quotes.forEach { quote ->
                jq(".${quote.exchange}-${quote.coin}").text("${quote.bid.round()} / ${quote.ask.round()}")
                it.quotes.filter { it.coin == quote.coin }.forEach { inner ->
                    val selector = "#${quote.coin} tr.buy${quote.exchange} td.vs${inner.exchange}"
                    formatPriceDiff(selector, (quote.bid - inner.ask))
                }
            }
            window.setTimeout({rest.getPrices (callback!!)}, 300)
        }

        rest.getPrices(callback)
    }
}

