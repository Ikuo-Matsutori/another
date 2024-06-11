package com.example.todoApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.sql.ResultSet


@Component//このクラスがSpringのコンポーネントとして管理されることを示す。
class TodoRowMapper : RowMapper <Todo>{//このクラスは、RowMapper Tod0 インターフェースを実装し、データベースの行をTodoオブジェクトにマッピングする。
    override fun mapRow(rs: ResultSet, rowNum: Int): Todo {//mapRowメソッドは、データベースのResultSetからTodoオブジェクトを生成。
        return Todo(rs.getLong(1), rs.getString(2))// 結果セットの1番目の列（id）を取得、結果セットの2番目の列（text）を取得
    }
}

@Repository
class TodoRepository(//TodoRepositoryクラスは、JdbcTemplateとTodoRowMapperを使用してデータベース操作を行う。
    @Autowired val jdbcTemplate: JdbcTemplate,//JdbcTemplateの依存性注入
    @Autowired val todoRowMapper: TodoRowMapper//todoRowMapperの依存性注入
) {
    fun getTodos(): Array<Todo> {//データベースからTODO項目を取得して、Todoの配列を返すメソッド。
        val todos = jdbcTemplate.query("SELECT id, text FROM todos", todoRowMapper)//SQLを実行し、結果をtodoRowMapperを使用してTodoオブジェクトにマッピング
        return todos.toTypedArray()//List<Tod0>をArray<Tod0>に変換
    }

    fun saveTodo(todoRequest: TodoRequest): Long{//新しいTODO項目をデータベースに保存するメソッド。
        jdbcTemplate.update("INSERT INTO todos (text) VALUES (?)", todoRequest.text)
        val ids = jdbcTemplate.query("SELECT id, text FROM todos", todoRowMapper)
        return ids[ids.size-1].id//SQL INSERTクエリを実行し、新しいTODO項目をデータベースに追加。TodoRequestオブジェクトからtextフィールドの値を取得。
    }
}