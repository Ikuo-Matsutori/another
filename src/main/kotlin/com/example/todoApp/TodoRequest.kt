package com.example.todoApp

import java.math.BigDecimal

data class CoinRequest(val ticker_symbol: String,
                       val quantity: BigDecimal,
                       val unit_price: BigDecimal,
                       val total_price: BigDecimal,
                       val wallet: String)


