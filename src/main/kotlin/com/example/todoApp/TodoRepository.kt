package com.example.todoApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.math.BigDecimal
import java.net.URISyntaxException
import java.sql.ResultSet


@Component
class CoinRowMapper : RowMapper <Coin>{
    override fun mapRow(rs: ResultSet, rowNum: Int): Coin {
        return Coin(rs.getLong(1),
                    rs.getString(2),
                    rs.getBigDecimal(3),
                    rs.getBigDecimal(4),
                    rs.getBigDecimal(5),
                    rs.getString(6)
        )
    }
}

@Repository
class CoinRepository(
    @Autowired val jdbcTemplate: JdbcTemplate,
    @Autowired val coinRowMapper: CoinRowMapper
) {
    fun getCoins(): Array<Coin> {
        val coins = jdbcTemplate.query("SELECT * FROM coins", coinRowMapper)
        return coins.toTypedArray()
    }

    fun saveCoin(coinRequest: CoinRequest): String{
        jdbcTemplate.update("INSERT INTO coins (ticker_symbol, quantity, unit_price, total_price, wallet) VALUES (?, ?, ?, ?, ?)",
            coinRequest.ticker_symbol, coinRequest.quantity, coinRequest.unit_price, coinRequest.total_price, coinRequest.wallet)
        return "Created"
    }

    fun deleteCoin(id:Long):String{
        jdbcTemplate.update("DELETE FROM coins WHERE id = ? ",id)
        return "Deleted"
    }

}
//------------------------------------------------------------------------------------





