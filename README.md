# event-sourcing-poc
Event Sourcing POC

This project aims to explain how to use event-sourcing to synchronize data between 3 services.
The problem is an education company, that needs to use 3 services: 1 for professors, 1 for Students and other for classrooms.
- professor-service 
- Student-service
- classroom-service

## Infrastructure Services
The project has an "infra" directory containing a docker-compose.yml file.

This docker-compose will start Kafka (with Zookeper) and mysql8 server.

To start servers, you should use:
```bash
docker-compose up
``` 

To stop servers, you should use:
```bash
docker-compose down
``` 

### MySQL Schema creation
After start servers, you need to create the schemas needed by the services.

To do that, you should connect in mysql using root/admin12345 credentials and execute the following commands:
```mysql
create schema professor;
create schema student;
create schema classroom;
```

## Execute Services
To execute services, you should have Java 11 and execute the following command in each project root folder:
```bash
gradle bootRun
```
You can access a Swagger interface to test endpoints at the following URLs:
- Professor Service: http://localhost:8081/swagger-ui.html
- Student Service: http://localhost:8082/swagger-ui.html
- Classroom Service: http://localhost:8083/swagger-ui.html

## What you can check
- You can create professor and students at its services. When you create, update or delete one, a message is published in kafka with the operation using the following payload (sample):
```json
{
  "type": "CREATE",
  "entity": "Professor",
  "payload": "{\"id\":\"12314\",\"name\":\"Professor 1\"}"
}
```
The message has the operation type (CREATE/UPDATE/DELETE), the entity type and the entity payload.
- Classroom service listen the kafka topic and use the message to synchronize its own repository.


# TODO List
- Test with one event topic
- Test transactions failures (kafka failure, database failure)
  - Salva no banco, erro ao publicar 
  - Publico com sucesso, erro no banco 
- Como lidar com eventos duplicados 
- sequenciamento das msgs 
- Exemplo de rewind logs. Aplicação le eventos historicos e repopula sua base. 
- Monitoramento de msgs. Tratamento de erros. 
- Compactação. Como evitar que dados nos topicos cresçam infinitamente.
