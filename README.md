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