# Launch new task
curl -XPOST localhost:9500/run -vs -u user:password -d @puzzle3x3.json \
-H "Content-Type: application/json" \
-H "Idempotency-Key: 9"

curl -XPOST localhost:9000/newPuzzleConfig -d @testPuzzleConfig.json -H "Content-Type: application/json"

curl localhost:9000/puzzleConfig/puzzle-1

curl -XPOST localhost:9500/run -vs -u user:password -d @tmp.json \
-H "Content-Type: application/json" \
-H "Idempotency-Key: 9"
