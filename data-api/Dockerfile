FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY mvnw mvnw
COPY .mvn .mvn
COPY data-api/pom.xml data-api/pom.xml
COPY data-api/src data-api/src

RUN chmod +x mvnw \
    && ./mvnw -f data-api/pom.xml clean package -DskipTests \
    && cp data-api/target/data-api-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=build /app/app.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
