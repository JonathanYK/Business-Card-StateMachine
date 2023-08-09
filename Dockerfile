# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn -Dmaven.test.skip=true package

# Stage 2: Run the application
FROM openjdk:17-oracle

LABEL maintainer="Yonatan Kalma <yonatan.kalma@gmail.com>"

# Set the JAR file path as an argument
ARG JAR_FILE=/app/target/*.jar

# Copy the built JAR file from the build stage to the runtime stage
COPY --from=build ${JAR_FILE} app.jar

# Expose port 8080 for the application
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]