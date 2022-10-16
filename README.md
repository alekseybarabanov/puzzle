## Puzzle assembler

This is a pet project for the author to demonstrate developer skills and 
is intended to play in some future a role of the reference book of 
technologies the author has dealt with.

### Puzzle rules
Puzzle consists of square puzzle pieces. Each piece has got a colored circle part (either 1/3 or 2/3) on each side.

![PuzzlePiece1](https://user-images.githubusercontent.com/12249892/195968243-19db6d33-4daa-4ad0-a9d9-f4da2917eac8.jpg)
![PuzzlePiece2](https://user-images.githubusercontent.com/12249892/195968248-582b0c88-7045-4140-b86c-128b1e8ddac5.jpg)

The aim of the puzzle is to assemble a map (N pieces in a row, K pieces in a column) where all balls are consistent. 

See the assembled example below with 3x3 puzzle map.

![PuzzleMap](https://user-images.githubusercontent.com/12249892/195967697-3277d89c-16a6-4e50-813a-059b9c4ac4b0.jpg)

### Project description
Project consists of the modules
* puzzle-api - spring boot application, user interacts with via RESTful interface.
* puzzle-assembler - spring boot application where puzzle is actually assembled in.
* puzzle-domain - library, that holds the domain objects
* puzzle-repository-hsqldb - spring boot application that holds some pre-defined puzzle configurations, stores run tasks and the assembling results.
* puzzle-repository-cassandra(missing at the moment) - store in cassandra db
* puzzle-storage-manager(missing at the moment) - spring application that reads puzzleConfigs/assembled puzzles and calls appropriate repository

Here is the schema of the data workflow
![PuzzleArchitecture (2)](https://user-images.githubusercontent.com/12249892/196051900-e623c498-1805-4843-b8ce-64ee9b385bf5.jpg)

Some notes about the solution
* evey app has to be scaled horizontally
* every app can be (and will be in some time) deployed to a cloud
* the implementation should embrace variety transport (HTTP1.1,HTTP2.0,GRPC,KAFKA) 
* the implementation should be in different programming languages (Java,Kotlin,Go)

### Deploy on a local machine
#### Requirements
Must have been installed
* Java (I run with Java 19, but other version should work as well)
* Apache Maven (Is needed for building the project. My version is 3.8.5)
* Apache Kafka (I have kafka_2.13-3.3.1 installed)

### Building the project
mvn clean install

### Deploy all applications on default ports
* run kafka. You may use misc/kafka.sh script to run it within kafka folder.
* ```cd ${project.dir}/misc```
* ```deploy_all_local.sh```

### Run test sample
```
sh puzzle3x3.sh 
```

## Next steps
### Coming in the near future
* improve puzzle-repository to store run configurations and the result puzzle. [#1](https://github.com/alekseybarabanov/puzzle/issues/1)
* puzzle-result module [#8](https://github.com/alekseybarabanov/puzzle/issues/8)
* cassandra as datasource [#12](https://github.com/alekseybarabanov/puzzle/issues/12)
* create puzzle config generator for an arbitrary puzzle field size [#6](https://github.com/alekseybarabanov/puzzle/issues/6)

### After that
* simple web GUI to run tasks, show results, apply spring security, [#14](https://github.com/alekseybarabanov/puzzle/issues/14)

### Coming later
* the ability to deploy to k8s [#4](https://github.com/alekseybarabanov/puzzle/issues/4)
* add istio support
* add monitoring dashboards for the project (prometheus [#9](https://github.com/alekseybarabanov/puzzle/issues/9), grafana [#10](https://github.com/alekseybarabanov/puzzle/issues/10))
* enriched dynamic web GUI with possibility to create puzzles, show history etc. (Angular, Typescript, web sockets)

## Known shortcomings
* the implementation finds 4 equivalent assemblies of the puzzle that differ in rotation.
* puzzle-repository is just an example of working with JPA. The database structure is not tuned from the point of view of productivity.

