package kafka

import (
	"chatting-service/config"
	"github.com/confluentinc/confluent-kafka-go/kafka"
)

type Kafka struct {
	cfg *config.Config

	// Publish 만 하기 때문에 Producer 만 가진다.
	producer *kafka.Producer
}

func NewKafka(cfg *config.Config) (*Kafka, error) {
	k := &Kafka{cfg: cfg}
	var err error
	if k.producer, err = kafka.NewProducer(&kafka.ConfigMap{
		// Kafka URL 주소
		"bootstrap.servers": cfg.Kafka.URL,
		// Client 식별할 ID
		"client.id": cfg.Kafka.GroupID,
		// 메시지 전송에서 고가용성을 위해 어디까지 메시지를 저장할 것인가?
		// all 로 하면 성능적으로 좋지 않겠지만 메시지 유실에는 문제 없다.
		"acks": "all",
	}); err != nil {
		return nil, err
	} else {
		return k, nil
	}
}

func (k *Kafka) PublishEvent(topic string, value []byte, ch chan kafka.Event) (kafka.Event, error) {
	if err := k.producer.Produce(&kafka.Message{
		TopicPartition: kafka.TopicPartition{
			Topic:     &topic,
			Partition: kafka.PartitionAny,
		},
		Value: value,
	}, ch); err != nil {
		return nil, err
	} else {
		return <-ch, nil
	}
}
