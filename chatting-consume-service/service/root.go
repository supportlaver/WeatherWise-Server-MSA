package service

import (
	"chatting-consume-service/repository"
	"encoding/json"
	"fmt"
	"log"
)

type Service struct {
	repository *repository.Repository
}

type ChatEvent struct {
	User     string
	Message  string
	RoomName string
}

func NewService(repository *repository.Repository) *Service {
	s := &Service{repository: repository}
	if err := s.repository.Kafka.RegisterSubTopic("message"); err != nil {
		panic(err)
	} else {
		go s.consumeKafkaMessages()
	}

	return s
}

func (s *Service) consumeKafkaMessages() {
	consumer := s.repository.Kafka.Consumer
	defer consumer.Close()

	for {
		msg, err := consumer.ReadMessage(-1) // Wait indefinitely for a new message
		if err != nil {
			log.Printf("Consumer error: %v (%v)\n", err, msg)
			continue
		}

		var event ChatEvent

		if err := json.Unmarshal(msg.Value, &event); err != nil {
			log.Printf("Failed to decode event: %v\n", err)
		} else {
			fmt.Printf("Received message: %+v\n", event)
			// 메시지 저장
			if err := s.repository.InsertChatting(event.User, event.Message, event.RoomName); err != nil {
				//TODO 추가적인 에러 처리
				log.Println("Failed To Chat", "err", err)
			}
		}
	}
}
