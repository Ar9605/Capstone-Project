FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# 1. Copy the Parent POM from the root
COPY pom.xml ./pom.xml

# 2. Copy the Config Server project into its own subfolder
COPY config-server/pom.xml ./config-server/pom.xml
COPY config-server/src ./config-server/src

# 3. Build from the Config Server directory
WORKDIR /app/config-server
RUN mvn clean package -DskipTests

# STAGE 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Note the path change here to pull from the subfolder
COPY --from=builder /app/config-server/target/config-server-*.jar app.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]