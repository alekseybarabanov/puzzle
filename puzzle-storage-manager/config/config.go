package config

type Config struct {
	KafkaConsumer KafkaConsumerConfig `yaml:"kafkaConsumer"`
}

type KafkaConsumerConfig struct {
	Topic      string            `yaml:"topic"`
	Properties map[string]string `yaml:"properties"`
}
