FROM eclipse-temurin:24-jdk AS build
WORKDIR /workspace

COPY mvnw pom.xml ./
COPY src ./src

RUN ./mvnw -DskipTests package

FROM eclipse-temurin:24-jre
WORKDIR /app

COPY --from=build /workspace/target/ticket-system-java-0.1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

