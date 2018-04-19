package cup

import dto.Quote

interface Getter {
    fun get(): Quote
    val coin: String
    val exchange: String
}
