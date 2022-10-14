# Launch new task
curl -XPOST localhost:9500/run -vs -u user:password -d @puzzle3x3.json \
-H "Content-Type: application/json" \
-H "Idempotency-Key: 9"

curl localhost:9000/details