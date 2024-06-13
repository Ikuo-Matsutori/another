package com.example.todoApp

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.*
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@RestController
@RequestMapping
class TodoController(@Autowired val coinRepository: CoinRepository) { //TodoControllerクラスの宣言。todoRepositoryという依存性をコンストラクタインジェクションしている。

    @CrossOrigin
    @GetMapping("/api/list")
    fun getCoins(): Array<Coin> {
        return coinRepository.getCoins()
    }

    @PostMapping("/api/list")
    fun saveCoin(@RequestBody coinRequest: CoinRequest): String{
        return coinRepository.saveCoin(coinRequest)
    }

    @DeleteMapping("/api/list/{id}")
    fun deleteCoin(@PathVariable id: Long): String{
        return coinRepository.deleteCoin(id)
    }
}

@Configuration
class RestConfig{
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}

@RestController
@RequestMapping
class SampleController(private val restTemplate: RestTemplate) {

    @GetMapping("/api/price/{ticker}")
    fun getPrice(@PathVariable ticker: String): ResponseEntity<Any> {

        // リクエスト送信先のURLを設定
        val baseUri: String = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest"

        // 送信先のパスとクエリーを設定
        val getInstanceUrl = UriComponentsBuilder.fromHttpUrl(baseUri)
            .queryParam("symbol", ticker)
            .queryParam("convert", "JPY")
            .build()
            .toUri()

        // リクエストヘッダを設定
        // APIキーの設定
        val apiKey: String = "2ace0d9f-a11f-41f2-9fa5-dc72abfa5fca"
        val headers = HttpHeaders()
        headers.set("X-CMC_PRO_API_KEY", apiKey)

        // HttpEntityにセット。
        val entity: HttpEntity<String> = HttpEntity(headers)
        return try {
            val responseEntity: ResponseEntity<String> = restTemplate.exchange(
                getInstanceUrl, HttpMethod.GET, entity, String::class.java
            )

            val mapper = ObjectMapper()
            val root: JsonNode = mapper.readTree(responseEntity.body)
            val price = root["data"][ticker]["quote"]["JPY"]["price"].asDouble()
            ResponseEntity.ok(price)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error")
        }
    }
}
