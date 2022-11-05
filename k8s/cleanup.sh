#!/bin/bash

# Delete resources
kubectl -n=$1 delete deploy -l factory=puzzle
kubectl -n=$1 delete cm -l factory=puzzle
kubectl -n=$1 delete svc -l factory=puzzle
sleep 10

# Stop minikube
minikube stop