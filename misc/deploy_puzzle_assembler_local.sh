#!/bin/bash

if [[ -z $1 ]]
then
  SERVER=""
else
  SERVER="-Dserver.port=$1"
fi

java $SERVER -DPOD_NAME=local -jar ../build-artifacts/puzzle-assembler-1.0-SNAPSHOT.jar
