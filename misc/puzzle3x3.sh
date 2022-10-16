#!/bin/bash

if [[ -z $1 ]]
then
  IDEMPOTENCY_KEY=`uuidgen | sed 's/-//g'`
else
  IDEMPOTENCY_KEY=$1
fi
echo "Echo: $IDEMPOTENCY_KEY"

# Launch new task
curl -XPOST localhost:9500/run -vsi -u user:password -d @puzzle3x3.json \
-H "Content-Type: application/json" \
-H "Idempotency-Key: $IDEMPOTENCY_KEY"