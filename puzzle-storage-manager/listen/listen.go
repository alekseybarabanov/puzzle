package listen

import (
	"encoding/json"
	"fmt"
	"os"
	"puzzle-storage-manager/config"
	"puzzle-storage-manager/domain"

	"github.com/confluentinc/confluent-kafka-go/kafka"
	"github.com/spf13/viper"
)

type KafkaListener struct {
	consumer *kafka.Consumer
	stopCh   <-chan struct{}
}

func NewKafkaListener(stopCh <-chan struct{}) *KafkaListener {
	return &KafkaListener{stopCh: stopCh}
}

func (l *KafkaListener) Listen() error {
	// Read configuration
	var cg config.KafkaConsumerConfig
	err := viper.UnmarshalKey("kafkaConsumer", &cg)
	if err != nil {
		return err
	}

	// Transform configuration to Kafka format
	consumerCfg := kafka.ConfigMap{}
	for k, v := range cg.Properties {
		consumerCfg[k] = v
	}

	// Create consumer
	consumer, err := kafka.NewConsumer(&consumerCfg)
	if err != nil {
		fmt.Printf("Failed to create consumer: %s", err)
		os.Exit(1)
	}
	err = consumer.SubscribeTopics([]string{cg.Topic}, nil)
	if err != nil {
		fmt.Printf("Failed to subscribe to topic: %s", err)
		os.Exit(1)
	}
	l.consumer = consumer
	fmt.Println("Listening to " + cg.Topic)

	// Listen for messages
	l.readLoop()

	fmt.Printf("Closing consumer\n")
	consumer.Close()
	return nil
}

func (l *KafkaListener) readLoop() {
	run := true

	for run {
		select {
		case sig := <-l.stopCh:
			fmt.Printf("Caught signal %v: terminating\n", sig)
			run = false
		default:
			ev := l.consumer.Poll(100)
			if ev == nil {
				continue
			}

			switch e := ev.(type) {
			case *kafka.Message:
				if e.TopicPartition.Offset > 15442 {
					fmt.Println("Offset: ", e.TopicPartition.Offset)
				}
				msg, err := l.deserializeMessage(e)
				if err != nil {
					fmt.Printf("Cannot deserialize message %s", err.Error())
					continue
				}
				if msg.IsCompleted {
					fmt.Printf("Solution found: %s\n", string(e.Value))
				}
				// fmt.Printf("Received message: %t\n", msg.IsCompleted)
				// _, err = l.consumer.StoreMessage(e)
				// if err != nil {
				// 	fmt.Printf("Failed to store message: %s", err.Error())
				// }
			case kafka.Error:
				fmt.Fprintf(os.Stderr, "%% Error: %v: %v\n", e.Code(), e)
				if e.Code() == kafka.ErrAllBrokersDown {
					run = false
				}
			default:
				fmt.Printf("Ignored %v\n", e)
			}
		}
	}
}

func (l *KafkaListener) deserializeMessage(msg *kafka.Message) (*domain.PuzzleState, error) {
	var puzzleState domain.PuzzleState
	err := json.Unmarshal(msg.Value, &puzzleState)
	if err != nil {
		return nil, err
	}
	return &puzzleState, nil

}
