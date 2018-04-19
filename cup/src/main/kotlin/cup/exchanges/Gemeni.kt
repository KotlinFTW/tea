package cup.exchanges

import dto.Quote
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import cup.Getter
import javax.ws.rs.client.ClientBuilder
import javax.xml.bind.annotation.XmlRootElement

//{"bid":"6688.39","ask":"6688.40","volume":{"BTC":"7794.4478141416","USD":"52942444.552739303361","timestamp":1522932000000},"last":"6692.62"}
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
class GemeniQuote {

    var bid: Double = 0.toDouble()
    var ask: Double = 0.toDouble()
    var volume: Volume? = null
    var last: Double = 0.toDouble()

    internal fun asQuote(symbol: String) = Quote(bid, ask, "gemeni", symbol)
}


@JsonIgnoreProperties(ignoreUnknown = true)
class Volume {
    var BTC: Double? = null
    var ETH: Double? = null
    var USD: Double? = null
    var timestamp: Long = 0

    override fun toString(): String {
        return "Volume(ETH=$ETH, USD=$USD, timestamp=$timestamp)"
    }

}

class GemeniGetter(override val coin: String) : Getter {

    private val target = ClientBuilder.newClient().target("https://api.gemini.com/v1/pubticker/" + coin.toLowerCase() + "usd")

    override fun get() = target.request().get(GemeniQuote::class.java).asQuote(coin)

    override val exchange= "gemeni"
}
