package main

import (
	"chatting-consume-service/cmd/app"
	"chatting-consume-service/config"
	"flag"
)

var pathFlag = flag.String("config", "./config.toml", "config set")
var port = flag.String("port", ":1011", "port set")

func main() {
	flag.Parse()
	c := config.NewConfig(*pathFlag)

	// TODO app 객체를 사용하여 서버 시작
	a := app.NewApp(c)

	a.Start()
}
