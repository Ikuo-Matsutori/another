package com.example.todoApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//@SpringBootApplicationのインポート文。 Spring Bootアプリケーションの設定を自動的に行うためのアノテーション
//runApplication関数を使用するためのインポート文。これにより、Spring Bootアプリケーションを起動できる。

@SpringBootApplication //このアノテーションは、Spring Bootアプリケーションのエントリーポイントであることを示す。
class TodoAppApplication //アプリケーションのメインクラスです。Spring Bootのエントリーポイントとして機能
fun main(args: Array<String>) {
	runApplication<TodoAppApplication>(*args)
}
//Kotlinのメイン関数 アプリケーションの実行時に最初に呼び出される関数
//Spring Bootアプリケーションを起動する関数。TodoAppApplicationクラスを引数として渡し、アプリケーションの設定・初期化を行う。
// *argsは可変長引数を展開するためのシンタックスで、コマンドライン引数をそのまま渡す。
