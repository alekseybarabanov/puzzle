# Delete kafka topic
./kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic puzzle-1

# start zookeeper
./zookeeper-server-start.sh ../config/zookeeper.properties

# start kafka
./kafka-server-start.sh ../config/server.properties