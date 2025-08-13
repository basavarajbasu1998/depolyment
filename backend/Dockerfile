# ----------- Build Stage -----------
FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app

COPY pom.xml . 
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# ----------- Runtime Stage -----------
FROM yethitech/tenjin-base-jdk:21-slim
WORKDIR /app

COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar ./

CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
