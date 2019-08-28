#!/bin/sh
mvn clean package && docker build -t com.enjoying/Homework .
docker rm -f Homework || true && docker run -d -p 8080:8080 -p 4848:4848 --name Homework com.enjoying/Homework 
