# Stage 1: Build the DTO project
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the project files
COPY . .

# Build the project and skip tests
RUN mvn clean install -DskipTests

# The built jar will be inside target/ directory, typically something like nextshow-dto-<version>.jar
