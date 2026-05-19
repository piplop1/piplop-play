# Etapa 1: Build con Gradle 9.5.0 y JDK 21 (compilacion)
FROM gradle:9.5.0-jdk21 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle bootjar --no-daemon

# Etapa 2: Runtime con JDK 21 (ejecución)
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar piplop_play.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "piplop_play.jar"]