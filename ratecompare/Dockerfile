# Use the official Maven image for a build stage
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn -f pom.xml clean package

# Use the official OpenJDK image for a lean production stage
FROM openjdk:17-slim
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]