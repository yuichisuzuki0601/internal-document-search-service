# 社内文書検索システム
* コピー&ペースト、リバースエンジニアリング、商用利用全て可です。
* 無料で自由にお使いください。

## 機能一覧
* 「高速検索機能」  
Elasticsearchによる高速文書検索機能

* 「走査対象ディレクトリ管理機能」  
文書が管理されているディレクトリを登録することで自動で検索エンジンへ取り込む機能

* 「翻訳ワード管理機能」  
予め登録した検索ワードを翻訳して別の検索文字列に変換する機能
(例:「山手線」 → 東京 or 神田 or 秋葉原...)

* 「データ定期削除機能」  
データの溜まり過ぎを防ぐために定期的に文書データを消去する機能

## 起動方法
コマンド実行は全てプロジェクトのルートディレクトリから実行します

### 〜設定の準備〜
* 以下コマンド実行

```
$ cd src/main/resources
$ cp application-variable.yml.example application-variable.yml
```

### 〜ミドルウェア環境用意〜
* javaのインストール
* dockerのインストール
* 以下コマンド実行

```
$ cd docker
$ docker-compose up -d
$ cd ../database
$ ./build.sh
```

### 〜クライアントビルド〜
* nodeとnpmのインストール(nodebrewがおすすめ)
* yarnのインストール
* 以下コマンド実行

```
$ cd client
$ yarn install
$ yarn build
```

### 〜サーバービルド〜
* 以下コマンド実行

```
$ ./mvnw clean package
```

### 〜起動〜
* 以下コマンド実行

```
$ java -jar target/internal-document-search-service-1.0.0.jar
```

* ブラウザからhttp://localhost:8080にアクセス
