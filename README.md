# event-sourcing-poc
Event Sourcing POC

This project aims to explain how to use event-sourcing to synchronize data between 3 services.
- academic-service: the service that owns the data and publish events in event-sourcing and event-carried-state-tranfer ways 
- avalia-service: the service that uses event-sourcing to syncronize application
- classroom-service: the service that uses event-carried-state-transfer to syncronize application

## Infrastructure Services
The project has an "infra" directory containing a docker-compose.yml file.

This docker-compose will start Kafka (with Zookeper), Redis and MySQL8 server.

To start servers, you should use:
```bash
docker-compose up
``` 

To stop servers, you should use:
```bash
docker-compose down
``` 

IMPORTANT: The MySQL and Redis data are persisted. The kafka topics doesn't. You can change the docker-compose to persist Kafka data too

### MySQL Schema creation
After start servers, you need to create the schemas needed by the services.

To do that, you should connect in mysql using root/admin12345 credentials and execute the following commands:
```mysql
create schema avalia;
create schema gaia;
```

## Execute Services
To execute services, you should have Java 11 and execute the following command in each project root folder:
```bash
gradle bootRun
```
You can access a Swagger interface to test CRUD in Academic service endpoint at the following URL:
- Academic Service: http://localhost:8081/swagger-ui.html

## What you can check
- When you create, update or delete entities, a message is published in kafka with the operation using the following payload (sample):
```json
{
  "type": "CREATE",
  "entity": "Professor",
  "payload": "{\"id\":\"12314\",\"name\":\"Professor 1\"}"
}
```
The message has the operation type (CREATE/UPDATE/DELETE), the entity type and the entity payload.

We have specific topics for each entity, to test event-carried way. For the message above, we publish a message in TABLE_PROFESSOR topic, using the id as a key and the followin payload as message body:
```json
{
  "id": "12314", 
  "name": "Professor 1"
}
```
The academic-service listen the first topic and the gaia-service listen each TABLE topic.
They should update its local services databases with the operations.

# To learn more
- Book - Making Sense of Stream Processing - https://www.confluent.io/stream-processing/
- Book - Designing Event-Driven Systems - https://www.confluent.io/designing-event-driven-systems/
- Apache Kafka - https://kafka.apache.org
- Redis - https://redis.io/
- MySQL - https://www.mysql.com/
- Spring Data Redis - https://spring.io/projects/spring-data-redis
- Spring Data JPA - https://spring.io/projects/spring-data-jpa
- SpringFox - https://springfox.github.io/springfox/
- Docker Compose - https://docs.docker.com/compose/gettingstarted/