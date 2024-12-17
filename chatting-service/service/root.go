package service

import (
	"chatting-service/repository"
	"chatting-service/types/schema"
	"encoding/json"
	"github.com/confluentinc/confluent-kafka-go/kafka"
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
	return s
}

func (s *Service) PublishServerStatusEvent(ip string, status bool) {
	type ServerInfoEvent struct {
		IP     string
		Status bool
	}

	e := &ServerInfoEvent{IP: ip, Status: status}

	ch := make(chan kafka.Event)

	if v, err := json.Marshal(e); err != nil {
		log.Println("Failed To Marshal")
	} else if result, err := s.PublishEvent("chat", v, ch); err != nil {
		log.Println("Failed To Send Event To Kafka", "err", err)
	} else {
		log.Println("Success To Send Event", result)
	}

}

func (s *Service) PublishEvent(topic string, value []byte, ch chan kafka.Event) (kafka.Event, error) {
	return s.repository.
		Kafka.PublishEvent(topic, value, ch)
}

func (s *Service) InsertChatting(user, message, roomName string) {
	// 여기서 Kafka 에 전달하고 끝
	event := ChatEvent{
		User:     user,
		Message:  message,
		RoomName: roomName,
	}
	eventData, err := json.Marshal(event)
	if err != nil {
		log.Println("Failed to Marshal ChatEvent", "err", err)
		return
	}

	// Kafka 이벤트 채널 생성
	eventChan := make(chan kafka.Event)

	// Kafka에 메시지 발행
	if result, err := s.PublishEvent("message", eventData, eventChan); err != nil {
		log.Println("Failed to Send Chat Event to Kafka", "err", err)
	} else {
		log.Println("Successfully Sent Chat Event to Kafka", "result", result)
	}
}

// GetChatList
func (s *Service) EnterRoom(roomName string) ([]*schema.Chat, error) {
	// 실제 실무에서도 이런 구조로 로그를 남기는 경우가 많다.
	if res, err := s.repository.GetChatList(roomName); err != nil {
		log.Println("Failed To Get Chat List", "err", err.Error())
		return nil, err
	} else {
		return res, nil
	}
}

// RoomList
func (s *Service) RoomList() ([]*schema.Room, error) {
	if res, err := s.repository.RoomList(); err != nil {
		log.Println("Failed To Get All Room List", "err", err.Error())
		return nil, err
	} else {
		return res, nil
	}

}

// MakeRoom
func (s *Service) MakeRoom(roomName string) error {
	if err := s.repository.MakeRoom(roomName); err != nil {
		log.Println("Failed To Make New Room", "err", err.Error())
		return err
	} else {
		return nil
	}

}

// Room
func (s *Service) Room(roomName string) (*schema.Room, error) {
	if res, err := s.repository.Room(roomName); err != nil {
		log.Println("Failed To Get Room", "err", err.Error())
		return nil, err
	} else {
		return res, nil
	}
}
