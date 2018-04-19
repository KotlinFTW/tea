package cup.exchanges
import dto.Quote
import cup.Getter
import javax.ws.rs.client.ClientBuilder

import javax.xml.bind.annotation.XmlRootElement

//{"date":"1522935954","ticker":{"high":"142.75","vol":"931.14","last":"135.36","low":"130.00","buy":"135.31","sell":"142.30"}}
@XmlRootElement
class OkCoin {
    var date: Long = 0
    var ticker: Ticker? = null

    fun asQuote(symbol: String) = Quote(ticker!!.buy, ticker!!.sell, "okcoin", symbol)
}


class Ticker {
    var high: Double = 0.0
    var low: Double = 0.0
    var vol: Double = 0.0
    var last: Double = 0.0
    var buy: Double = 0.0
    var sell: Double = 0.0
}

class OKCoinGetter(override val coin: String) : Getter {

    private val target = ClientBuilder.newClient().target("https://www.okcoin.com/api/v1/ticker.do?symbol=" + coin.toLowerCase() + "_usd")

    override fun get() = target.request().get(OkCoin::class.java).asQuote(coin)

    override val exchange = "okcoin"

}
