package config

import (
	"testing"

	"github.com/stretchr/testify/assert"
	"gopkg.in/yaml.v3"
)

func TestConfig(t *testing.T) {
	t.Run("Marshal", func(t *testing.T) {
		config := Config{
			KafkaConsumer: KafkaConsumerConfig{
				Topic:      "test",
				Properties: map[string]string{"propA": "valA", "propB": "valB"},
			},
		}
		cf, err := yaml.Marshal(&config)
		assert.NoError(t, err)
		assert.EqualValues(t, "kafkaConsumer:\n    brokers:\n        - localhost:9092\n    group: test\n    topic: test\n    properties:\n        propA: valA\n        propB: valB\n", string(cf))
	})
	t.Run("Unmarshal", func(t *testing.T) {
		str := `
kafkaConsumer:
  topic: test
  properties:
    propA: valA
    propB: valB
`
		var config Config
		err := yaml.Unmarshal([]byte(str), &config)
		assert.NoError(t, err)
		assert.EqualValues(t, "test", config.KafkaConsumer.Topic)
		assert.EqualValues(t, "valA", config.KafkaConsumer.Properties["propA"])
	})
}
