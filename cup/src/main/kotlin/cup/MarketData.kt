package cup

import dto.*
import cup.exchanges.CexGetter
import cup.exchanges.GdaxGetter
import cup.exchanges.GemeniGetter
import cup.exchanges.OKCoinGetter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


class MarketData {

    val lock = ReentrantReadWriteLock()

    val prices: MutableMap<String, MutableMap<String, Quote>> = mutableMapOf()

    val pool = Executors.newScheduledThreadPool(4)

    init
    {
        val interval = 5L
        val coins = listOf("btc", "eth")
        pool.scheduleAtFixedRate(PollTask(coins.map { GdaxGetter(it) }), 0, interval, TimeUnit.SECONDS)
        pool.scheduleAtFixedRate(PollTask(coins.map { OKCoinGetter(it) }), 1, interval, TimeUnit.SECONDS)
        pool.scheduleAtFixedRate(PollTask(coins.map { CexGetter(it) }), 2, interval, TimeUnit.SECONDS)
        pool.scheduleAtFixedRate(PollTask(coins.map { GemeniGetter(it) }), 3, interval, TimeUnit.SECONDS)
    }

    //todo: use websockets instead of polling
    inner class PollTask(private val getters: List<Getter>): Runnable {
        override fun run() {
            getters.forEach {
                try {
                    println("Get ${it.coin} from ${it.exchange}");
                    val quote = it.get()
                    println("got $quote")
                    lock.write {
                        if (!prices.containsKey(it.coin)) {
                            prices[it.coin] = mutableMapOf()
                        }
                        prices[it.coin]!![it.exchange] = quote
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    fun getPrices(): Prices = lock.read {
        val result = mutableListOf<Quote>()
        prices.forEach{
            result.addAll(it.value.values)
        }
        return Prices(result.toTypedArray())
    }
}
