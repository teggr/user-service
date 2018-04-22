# user-service

A simple user service with a Restful endpoint

* Implemented using Spring Boot starter project (spring web, spring data jpa)
* Uses mysql-connector-java JDBC connector

# Instructions

Dependencies

* Running mysql db (details below)
* Java 8
* Maven

## Running the application

To run application using maven

	mvn clean spring-boot:run 
	
To run as stand-alone java application

	mvn clean package
	
	java -jar target/user-service.jar

## MySql Database

This service requires a running databse in order to save Users. JPA currently uses create-drop to load schema

* Location - localhost:3306/user_db
* Username - client
* Password - clientPassword1

### MySql on docker

Container available here: https://hub.docker.com/_/mysql/

	# start up the container
	docker run --name user-data -e MYSQL_ROOT_PASSWORD=password1 -e MYSQL_DATABASE=user_db -e MYSQL_USER=client -e MYSQL_PASSWORD=clientPassword1 -p 3306:3306 -d mysql:5.7.22
	
	# run commands within container
	docker exec -it user-data bash

## Creating user

POST a JSON request to the following endpoint 

	http://localhost:8080/api/user/add

with a payload as described below

	{
		"username": "TestUser",
		"firstname": "John",
		"lastname": "Doe",
		"age": 25
	}
