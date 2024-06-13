package com.example.todoApp
import java.math.BigDecimal

data class Coin(val id: Long,
                val ticker_symbol: String,
                val quantity: BigDecimal,
                val unit_price: BigDecimal,
                val total_price: BigDecimal,
                val wallet: String)
