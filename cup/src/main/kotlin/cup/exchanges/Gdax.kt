package cup.exchanges
import dto.Quote
import cup.Getter
import javax.ws.rs.client.ClientBuilder


import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class GdaxQuote {
    //{"sequence":5610373061,"bids":[["6674.99","9.44403492",12]],"asks":[["6675","2.28367358",22]]}

    var sequence: Long = 0
    var bids: Array<Array<String>>? = null
    var asks: Array<Array<String>>? = null

    internal fun asQuote(symbol: String) = Quote(bids!![0][0].toDouble(), asks!![0][0].toDouble(), "gdax", symbol)
}

class GdaxGetter(override val coin: String) : Getter {

    private val target = ClientBuilder.newClient().target("https://api.gdax.com/products/" + coin.toUpperCase() + "-USD/book")

    override fun get() = target.request().get(GdaxQuote::class.java).asQuote(coin)

    override val exchange = "gdax"
}
