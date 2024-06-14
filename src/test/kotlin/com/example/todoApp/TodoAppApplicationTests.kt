package com.example.todoApp

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@Sql("/insert_test_data.sql")
class TodoAppApplicationTests(
	@Autowired val restTemplate: TestRestTemplate,
	@LocalServerPort val port: Int
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `GETリクエストはOKステータスを返す`() {
		val response = restTemplate.getForEntity("http://localhost:$port/api/list", String::class.java)
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはTodoオブジェクトのリストを返す`() {
		val response = restTemplate.getForEntity("http://localhost:$port/api/list", Array<Coin>::class.java)
		val coins = response.body!!
		assertThat(coins[0].id, equalTo(1))
		assertThat(coins[1].id, equalTo(2))
	}

	@Test
	fun `POSTリクエストはOKステータスを返す`() {
		val request = CoinRequest("BTC", BigDecimal(1) , BigDecimal(200),BigDecimal(200),"DMM")
		val response = restTemplate.postForEntity("http://localhost:$port/api/list", request, String::class.java)
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}
}

