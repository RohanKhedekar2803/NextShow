# Stage 1: Build the application using Maven with JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

# Copy the Maven settings and project files
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml .
COPY src ./src

# Build the application and remove unnecessary files
RUN mvn clean package -s /root/.m2/settings.xml -DskipTests && \
    rm -rf /build/src /build/pom.xml /root/.m2

# Stage 2: Use lightweight Java runtime for JAR
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Remove unnecessary locales and docs to save space
RUN rm -rf /usr/share/zoneinfo /usr/share/man /usr/share/doc /usr/share/info /tmp/*

# Copy the generated JAR file from the build stage
COPY --from=build /build/target/events-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app will run on
RUN apk add --no-cache curl

# Run the JAR file with Java
ENTRYPOINT ["java", "-Xmx128m", "-Xms64m", "-jar", "app.jar"]

