package main

import (
	"fmt"
	"os"
	"os/signal"
	"puzzle-storage-manager/listen"
	"syscall"
	"time"

	"github.com/spf13/viper"
)

func main() {
	fmt.Println("starting...")
	stopCh := make(chan struct{})
	defer close(stopCh)

	// Read config file
	readConfigFile()

	// Run kafka listeger
	l := listen.NewKafkaListener(stopCh)
	go l.Listen()

	// Read OS signals to stop application
	cancelChan := make(chan os.Signal, 1)
	signal.Notify(cancelChan, syscall.SIGTERM, syscall.SIGINT)
	sg := <-cancelChan
	fmt.Println("received signal:", sg.String())
	stopCh <- struct{}{}
	time.Sleep(time.Second)
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
