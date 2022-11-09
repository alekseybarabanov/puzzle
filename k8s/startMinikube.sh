#!/bin/bash

# Configure minikube
minikube config set memory 6000mb
minikube config set cpus 8

# Run minikube
minikube start

# Create tunnel from localhost to minikube
minikube tunnel &
