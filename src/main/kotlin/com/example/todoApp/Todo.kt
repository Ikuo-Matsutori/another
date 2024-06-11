package com.example.todoApp
//パッケージ宣言　このコードが　com.example.todoApp　という名前空間に属していることを示す。
data class Todo(val id: Long, val text: String)
//data class: Kotlinの特別なクラスで、データを保持するために使用。dataキーワードを使用することで、
// データクラスは自動的にいくつかの便利なメソッド（toString, equals, hashCode, copyなど）を生成。
