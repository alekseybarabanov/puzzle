# list processes listening to the port <port number>
sudo lsof -i -P | grep LISTEN | grep <port number>

# kill process
kill -9 <pid>
