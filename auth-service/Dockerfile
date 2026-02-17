# STAGE 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# This builds the jar file inside the container
RUN mvn clean package -DskipTests

# STAGE 2: Run the application using a lightweight JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy only the built JAR from Stage 1, leaving the heavy Maven tools behind
COPY --from=builder /app/target/*.jar app.jar

# Start the application
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]