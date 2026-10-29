FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY mvnw mvnw
COPY .mvn .mvn
COPY auth-api/pom.xml auth-api/pom.xml
COPY auth-api/src auth-api/src

RUN chmod +x mvnw \
    && ./mvnw -f auth-api/pom.xml clean package -DskipTests \
    && cp auth-api/target/auth-api-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /app/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
