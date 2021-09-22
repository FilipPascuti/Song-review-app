#
# Build stage
#
#FROM gradle:7.0.2-jdk8 AS build
#COPY ./src /app/src
#COPY build.gradle /app
#RUN gradle -b /app/build.gradle
#
##
## Package stage
##
#FROM openjdk:8-jre-slim
#COPY --from=build ./app/build/libs/*.jar /usr/local/lib/gateway.jar
#ENTRYPOINT ["java","-jar","/usr/local/lib/gateway.jar"]

##
## Build stage
##
#FROM maven:3.5-jdk-8 AS build
#COPY pom.xml .
#COPY backend/medical-app/src /backend/app/src
#COPY backend/medical-app/pom.xml /backend/app
#RUN mvn -f /backend/app/pom.xml clean package
#
##
## Package stage
##
#FROM openjdk:8-jre-slim
#COPY --from=build /backend/app/target/app-1.0.jar /usr/local/lib/medical-app.jar
#ENTRYPOINT ["java","-jar","/usr/local/lib/medical-app.jar"]


#FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/gateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
