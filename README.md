# coin-map

このアプリケーションでは CoinMarketCap-API の情報を元に暗号資産の一括管理を行います。

利点
・CoinMarketCap-API 　に登録されている 20,000 種類以上の暗号資産データを利用できます。
・複数の取引所で購入した暗号資産を一括管理し、損益状況を計算します。
・損益状況に応じてコメントが表示されます。

注意点
・現在価格及び損益は　 CoinMarketCap-API 　のデータを元に算出しているため、
　実際に取引する際の金額とは差異が生じます。損益値は資産管理の目安として
　実際の売買利益は各取引所の値を確認してください。

ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
設定方法

1. inteliJでファイルを開いてください
2. frontend ファイルで npm i を実行してください。
3. frontend で npm run dev を実行してください。
4. psql でローカル環境に db を作成してください。
5. src/main/resources/application.properties の環境変数を設定してください。
   spring.application.name=todoApp
   spring.datasource.url=jdbc:postgresql://localhost/<yout db name>
   spring.datasource.username=user<your user name>
   spring.datasource.password=<your password>
   spring.datasource.driverClassName=org.postgresql.Driver
6. application/bootRun を実行し、サーバーを立ち上げてください
7. ブラウザでローカルホストにアクセスしてください　 http://localhost:8080/

ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
使用方法

1. 入力ダブに取引データを入力し、「追加」ボタンを押してください。
   　　一覧表に入力したデータが追加され、損益状況が計算されます。
2. 売却や入力ミスによりデータを削除したい場合は「消去」ボタンを押してください。
3. 損益計：　の右側に総損益状況が表示されます。
4. 暗号資産の買いすぎに注意しましょう。当アプリケーションでは一切の責任を取りません。