package main

import (
	"fmt"
	"puzzle-storage-manager/listen"

	"github.com/spf13/viper"
)

func main() {
	fmt.Println("starting...")
	stopCh := make(chan struct{})

	// Read config file
	readConfigFile()

	// Run kafka listeger
	l := listen.NewKafkaListener(stopCh)
	l.Listen()
}

func readConfigFile() {
	viper.AddConfigPath("./misc")
	viper.SetConfigName("config")
	viper.SetConfigType("yaml")
	err := viper.ReadInConfig()
	if err != nil {
		panic(fmt.Errorf("fatal error config file: %w", err))
	}
}
