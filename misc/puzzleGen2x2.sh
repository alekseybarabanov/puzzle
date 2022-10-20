#!/bin/bash

# Launch new task
curl -XPOST localhost:8400/generate -vsi -u user:password -d @puzzleGen2x2.json \
-H "Content-Type: application/json"