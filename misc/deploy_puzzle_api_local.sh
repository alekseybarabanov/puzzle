#!/bin/bash

if [[ -z $1 ]]
then
  SERVER=""
else
  SERVER="-Dserver.port=$1"
fi

java $SERVER -jar ../build-artifacts/puzzle-api-1.0-SNAPSHOT.jar
