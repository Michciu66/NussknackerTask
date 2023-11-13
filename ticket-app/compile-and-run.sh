#!/bin/bash

./mvnw clean compile package -Dmaven.test.skip=true


java -jar target/ticket-app-0.0.1-SNAPSHOT-exec.jar
