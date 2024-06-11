package com.example.todoApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController //クラスがRESTコントローラーであることを示す。 このクラスのメソッドは、JSONなどの形式でHTTPレスポンスを返す。
@RequestMapping("/")
class TodoController(@Autowired val todoRepository: TodoRepository) { //TodoControllerクラスの宣言。todoRepositoryという依存性をコンストラクタインジェクションしている。

    @CrossOrigin
    @GetMapping("/todos") //HTTP GETリクエストが/todosに送られたときにこのメソッドが呼び出される。
    fun getTodos(): Array<Todo> { //getTodosメソッド宣言。TODO項目の配列を返す。
        return todoRepository.getTodos() //TodoRepositoryからTODO項目のリストを取得して返す。
    }
//
    @PostMapping("/todos") //HTTP POSTリクエストが/todosに送られたときにこのメソッドが呼び出される。
    fun saveTodo(@RequestBody todoRequest: TodoRequest): Long{ //saveTodoメソッド宣言。TODO項目を保存し、文字列を返す。
    //RequestBodyアノテーションを使って、リクエストボディからTodoRequestオブジェクトをメソッドの引数としてバインドする。
        todoRepository.saveTodo(todoRequest) //TodoRepositoryを使ってTODO項目を保存。
        println("////////////////////////////")
        println(todoRepository.saveTodo(todoRequest))
        return todoRepository.saveTodo(todoRequest)
    }
}