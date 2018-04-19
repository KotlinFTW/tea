package cup.exchanges

import dto.Quote
import cup.Getter
import javax.ws.rs.client.ClientBuilder
import javax.xml.bind.annotation.XmlRootElement

class CexGetter(override val coin: String) : Getter {

    private val target = ClientBuilder.newClient().target("https://cex.io/api/order_book/" + coin.toUpperCase() + "/USD")

    override val exchange = "cex"

    override fun get() = target.request().get(CexQuote::class.java).asQuote(coin)
}


//"pair":"BTC:USD","id":240180009,"sell_total":"2036.69452514","buy_total":"5972932.52"
@XmlRootElement
class CexQuote {

    var timestamp: Long = 0
    var bids: Array<Array<String>>? = null
    var asks: Array<Array<String>>? = null
    var pair: String? = null
    var id: Long = 0
    var sell_total: Double = 0.toDouble()
    var buy_total: Double = 0.toDouble()

    fun asQuote(symbol: String) = Quote(java.lang.Double.valueOf(bids!![0][0]), java.lang.Double.valueOf(asks!![0][0]), "cex", symbol)

}
