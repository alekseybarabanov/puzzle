#!/bin/bash

# Run minikube
minikube start

# Create tunnel from localhost to minikube
minikube tunnel &
sleep 10

# Set current namespace (it is 'default' here)
export ns=default

# Apply deployments and services
kubectl -n=$ns apply -f puzzleApiDeploy.yaml
sleep 5
until [ $(kubectl -n=$ns get deploy puzzle-api -o jsonpath='{.status.conditions[?(.type=="Available")].status}') == "True" ];
do
  echo "Waiting for pods to deploy"
  echo $(kubectl -n=$ns get deploy -l factory=puzzle)
  sleep 10
done
echo "Minimum pod requirement is satisfied."
sleep 10

# Get puzzle-api cluster IP
export puzzleApiHost=$(kubectl -n=$ns get svc puzzle-api -o jsonpath='{.spec.clusterIP}{"\n"}')
echo "Service host api: $puzzleApiHost"

# Test access to the puzzle-api service
curl http://$puzzleApiHost:9500/actuator/health