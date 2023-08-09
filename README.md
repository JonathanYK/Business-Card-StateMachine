# Business-Card-StateMachine

In this project, I have developed a Spring Boot Java application using Maven to manage business card states using a state machine. The application includes a REST controller with the following endpoints, synchronizing data to a PostgreSQL database through the Spring JPA access layer.

API Endpoints
(Postman collection json [here](https://github.com/JonathanYK/Business-Card-StateMachine/blob/main/postman/BusinessCard-Testing.postman_collection.json)):
```
http://localhost:8080/card/all                             - GET all cards from PostgreSQL DB
http://localhost:8080/card/add                             - POST a new card to PostgreSQL DB (as a JSON file):
http://localhost:8080/card/verify?cardId={candId}          - PUT start verification process for a card by cardId
http://localhost:8080/card/manual?cardId={candId}          - PUT manually approve a card by cardId
http://localhost:8080/card/unverify?cardId={candId}        - PUT revert a manually approved card to a known state by cardId
http://localhost:8080/card/specificState/{state}           - GET all cards with a specific state from the DB

Dockerized / JAR Execution:
IP: localhost
PORT: 8080

PostgreSQL Configuration:
IP: localhost
PORT: 5432
```

To execute this application, the following environment variable must be defined:

```
DB_URL
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
TRUSTED_SOURCE_KEY
STRONG_VALIDATION_PHRASE
```
