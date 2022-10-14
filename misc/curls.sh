# Launch new task
curl -XPOST localhost:9500/run -vs -H "Content-Type: application/json" -d '{"testId":"2"}'

curl -XPOST localhost:9500/run -vs -H "Content-Type: application/json" -d @puzzle2Details.json