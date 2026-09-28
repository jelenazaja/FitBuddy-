# Stage 1, builds maven project in a large JDK based image
FROM maven:3.9-eclipse-temurin-25-noble AS build-stage
WORKDIR /opt/workout-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Stage 2, creates a JRE based image with compiled app
FROM eclipse-temurin:25-jre-alpine
WORKDIR /opt/workout-service
COPY --from=build-stage /opt/workout-service/target/*.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]