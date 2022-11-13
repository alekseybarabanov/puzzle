package listen

import (
	"fmt"
	"os"
	"puzzle-storage-manager/config"

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
				fmt.Printf("%% Message on %s:\n%s\n",
					e.TopicPartition, string(e.Value))
				if e.Headers != nil {
					fmt.Printf("%% Headers: %v\n", e.Headers)
				}
				_, err := l.consumer.StoreMessage(e)
				if err != nil {
					fmt.Fprintf(os.Stderr, "%% Error storing offset after message %s:\n",
						e.TopicPartition)
				}
			case kafka.Error:
				// Errors should generally be considered
				// informational, the client will try to
				// automatically recover.
				// But in this example we choose to terminate
				// the application if all brokers are down.
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
