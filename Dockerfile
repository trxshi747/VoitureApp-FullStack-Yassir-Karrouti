# Étape 1 : Build de l'application avec Gradle
FROM gradle:8.7-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# Étape 2 : Exécution de l'application
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
