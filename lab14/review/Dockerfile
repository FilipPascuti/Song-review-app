##
## Build stage
##
#FROM gradle:7.0.2-jdk8 AS build
#COPY build.gradle .
#COPY review/src /app/src
#COPY review/build.gradle /app
#RUN gradle build -f /review/build.gradle
#
##
## Package stage
##
#FROM openjdk:8-jre-slim
#COPY --from=build /app/target/app-1.0.jar /usr/local/lib/review.jar
#ENTRYPOINT ["java","-jar","/usr/local/lib/review.jar"]

#FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/review-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
