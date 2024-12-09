package kafka

import (
	"chatting-consume-service/config"
	"github.com/confluentinc/confluent-kafka-go/kafka"
)

type Kafka struct {
	cfg      *config.Config
	Consumer *kafka.Consumer
}

func NewKafka(cfg *config.Config) (*Kafka, error) {
	k := &Kafka{cfg: cfg}
	var err error
	// TODO Consumer
	if k.Consumer, err = kafka.NewConsumer(&kafka.ConfigMap{
		"bootstrap.servers": cfg.Kafka.URL,
		"group.id":          cfg.Kafka.GroupID,
		"auto.offset.reset": "latest",
	}); err != nil {
		return nil, err
	} else {
		return k, nil
	}
}

// Kafka 컨슈머가 어떤 토픽을 Subscribe 할지
func (k *Kafka) RegisterSubTopic(topic string) error {
	if err := k.Consumer.Subscribe(topic, nil); err != nil {
		return err
	} else {
		return nil
	}
}

func (k *Kafka) Pool(timeoutMs int) kafka.Event {
	return k.Consumer.Poll(timeoutMs)
}
