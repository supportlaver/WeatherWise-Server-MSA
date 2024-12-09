package app

import (
	"chatting-consume-service/config"
	"chatting-consume-service/network"
	"chatting-consume-service/repository"
	"chatting-consume-service/service"
)

// controller tower
type App struct {
	cfg *config.Config

	// repository
	repository *repository.Repository

	// service
	service *service.Service

	// network
	network *network.Server
}

func NewApp(cfg *config.Config) *App {
	a := &App{cfg: cfg}

	var err error
	if a.repository, err = repository.NewRepository(cfg); err != nil {
		panic(err)
	} else {
		a.service = service.NewService(a.repository)
		a.network = network.NewNetwork(a.service)
	}

	return a
}

func (a *App) Start() error {
	return a.network.Start()
}
