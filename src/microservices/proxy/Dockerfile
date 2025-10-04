#1: сборка
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

#2: образ
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Порт по умолчанию — можно переопределить через переменную окружения
ENV PORT=8000
EXPOSE $PORT

# Запуск с поддержкой динамического порта через переменную
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]