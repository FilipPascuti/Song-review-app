#FROM gradle:7.0.2-jdk8 AS build
#COPY ./src /app/src
#COPY build.gradle /app
#RUN gradle -b /app/build.gradle
#
##
## Package stage
##
#FROM openjdk:8-jre-slim
#COPY --from=build /app/build/libs/songs-0.0.1-SNAPSHOT.jar /usr/local/lib/gateway.jar
#ENTRYPOINT ["java","-jar","/usr/local/lib/gateway.jar"]

#FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/songs-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
