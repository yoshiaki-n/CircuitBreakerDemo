# CircuitBreakerDemo
Spring Resilience4j のデモアプリです。

このアプリは、SpringBootのREST APIアプリになっており、各APIを呼び出すと、
外部サービスを呼び出します。外部サービス呼び出し時にはサーキットブレーカーが有効になっています。

サーキットブレーカーの動作確認が出来る単体テストを含んでいます。

## パッケージ構成

mainのパッケージ構成は以下の通りとなっています。  
また、`package-info.java`も作成しているのでそちらも参考にして下さい。

```
./src/main/java/
└── com
    └── example
        └── circuitbreakerdemo
            ├── CircuitbreakerDemoApplication.java ……… エントリーポイントとなるクラスです。
            ├── controller ……… コントローラークラス(REST APIクラス)を配置しています。
            ├── infrastructure ……… レイヤ横断で使用するクラスや各種設定クラスを配置しています。
            │   ├── circuitbreaker ……… サーキットブレーカーの設定や定数を配置してます。
            │   └── httpclient ……… HTTPクライアントの設定を配置しています。
            └── service ……… ユースケースクラス、DDDで言うところのアプリケーションサービスクラスを配置しています。
./src/main/resources
├── application.yaml ……… アプリケーションの各種設定ファイル（サーキットブレーカーやログの設定があります。）
└── logback.xml ……… ログの設定ファイル
```


## テストについて

`CircuitbreakerdemoApplicationTests` クラスでサーキットブレーカーの動作確認を行うテストを書いています。  
外部サービスは`WireMock`を使っています。

テストは、`SpringBootTest`でアプリを起動して、外部サービス想定のモックサーバにアクセスしています。  
そのため、`8080`と`9090`ポートが空いている必要があります。

## リンク

-　[Spring Cloud Circuit Breaker リファレンス](https://spring.pleiades.io/spring-cloud-circuitbreaker/docs/current/reference/html/spring-cloud-circuitbreaker.html)