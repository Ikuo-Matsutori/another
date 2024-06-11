package com.example.todoApp

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.RestTemplate
import java.awt.PageAttributes

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//SpringBootTestアノテーションは、Spring Bootアプリケーションの完全なコンテキストをロードし、統合テストを実行。
//webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORTは、テスト用にランダムなポートでWebサーバーを起動する設定。
// これにより、同時に複数のテストが実行されてもポートの競合が発生しない。
@Sql("/insert_test_data.sql")//テスト前に指定されたSQLスクリプトを実行し、データベースに初期データを挿入。
class TodoAppApplicationTests(
	@Autowired val restTemplate: TestRestTemplate,//TestRestTemplateは、統合テスト用のRestTemplateです。HTTPリクエストを送信して、レスポンスを受け取るために使用します。
	@LocalServerPort val port: Int//@LocalServerPortは、ランダムに割り当てられたポート番号をインジェクトする。このポート番号を使用し、テスト対象のサーバーにリクエストを送信。
) {

	@Test
	fun contextLoads() {//コンテキストが正しくロードされることを確認するための空のテストメソッド。Spring Bootアプリケーションが正常に起動できるかを確認。
	}

	@Test
	fun `最初のテスト`() {
		assertThat(1+2, equalTo(3))
	}

	@Test
	fun `GETリクエストはOKステータスを返す`() {
		// localhost/todos に GETリクエストを発行する。
		val response = restTemplate.getForEntity("http://localhost:$port/todos", String::class.java)
		// レスポンスのステータスコードは OK である。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはTodoオブジェクトのリストを返す`() {
		val response = restTemplate.getForEntity("http://localhost:$port/todos", Array<Todo>::class.java)
//		assertThat(response.headers.contentType, equalTo(PageAttributes.MediaType.APPLICATION_JSON))
		val todos = response.body!!
		assertThat(todos.size, equalTo(2))
		assertThat(todos[0].id, equalTo(1))
		assertThat(todos[0].text, equalTo("foo"))
		assertThat(todos[1].id, equalTo(2))
		assertThat(todos[1].text, equalTo("bar"))
	}

	@Test
	fun `POSTリクエストはOKステータスを返す`() {
		// localhost/todos に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val request = TodoRequest("hello")
		val response = restTemplate.postForEntity("http://localhost:$port/todos", request, String::class.java)
		// レスポンスのステータスコードは OK であること。
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `POSTリクエストはTodoオブジェクトを格納する`() {
		// localhost/todos に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		val response1 = restTemplate.getForEntity("http://localhost:$port/todos", Array<Todo>::class.java)
		// このときのレスポンスを todos1 として記憶。
		val todos1 = response1.body!!
		// localhost/todos に POSTリクエストを送る。このときのボディは {"text": "hello"}
		val request = TodoRequest("hello")
		restTemplate.postForEntity("http://localhost:$port/todos",request, String::class.java)
		// ふたたび localhost/todos に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		val response2 = restTemplate.getForEntity("http://localhost:$port/todos", Array<Todo>::class.java)
		// このときのレスポンスを todos2 として記憶。
		val todos2 = response2.body!!
		// 配列 todos2 は、配列 todos1 よりも 1 要素だけ多い。
		assertThat(todos2.size, equalTo(todos1.size + 1))
		// 配列 todos2 には "hello" をもつTodoオブジェクトが含まれている。
		assertThat(todos2.map { todo: Todo -> todo.text }, hasItem("hello"))
	}
}

