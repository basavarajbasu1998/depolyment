# ------------ Stage 1: Build ------------
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# ------------ Stage 2: Run ------------
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
