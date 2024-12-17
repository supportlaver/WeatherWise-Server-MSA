package repository

import (
	"chatting-consume-service/config"
	"chatting-consume-service/repository/kafka"
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"log"
)

type Repository struct {
	cfg   *config.Config
	db    *sql.DB
	Kafka *kafka.Kafka
}

const (
	room       = "chatting.room"
	chat       = "chatting.chat"
	serverInfo = "chatting.serverInfo"
)

func NewRepository(cfg *config.Config) (*Repository, error) {
	r := &Repository{cfg: cfg}
	var err error

	if r.db, err = sql.Open(cfg.DB.Database, cfg.DB.URL); err != nil {
		return nil, err
	} else if r.Kafka, err = kafka.NewKafka(cfg); err != nil {
		return nil, err
	} else {
		return r, nil
	}
}

func (s *Repository) InsertChatting(user, message, roomName string) error {
	log.Println("Insert Chatting Using WSS", "from", user, "message", message, "room", roomName)
	_, err := s.db.Exec("INSERT INTO chatting.chat(room , name , message) VALUES(?,?,?)", roomName, user, message)
	return err
}
