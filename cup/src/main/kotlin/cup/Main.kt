package cup


fun main(args: Array<String>) {

    val rest = attachNetty(8081, listOf(MarketDataService(), WebService()))

    rest.start()
    println("server started on port ${rest.port}")
}
