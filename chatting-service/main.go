package main

import (
	"chatting-service/config"
	"chatting-service/network"
	"chatting-service/repository"
	"chatting-service/service"
	"flag"
)

var pathFlag = flag.String("config", "./config.toml", "config set")
var port = flag.String("port", ":1010", "port set")

func main() {
	flag.Parse()
	c := config.NewConfig(*pathFlag)

	if rep, err := repository.NewRepository(c); err != nil {
		panic(err)
	} else {
		s := network.NewServer(service.NewService(rep), *port)
		s.StartServer()
	}
}
