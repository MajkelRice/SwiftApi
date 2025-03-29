FROM gradle:jdk21-alpine AS builder
WORKDIR /app

COPY . .

RUN chmod +x ./gradlew && \
    ./gradlew bootJar --no-daemon

FROM openjdk:21-slim
WORKDIR /app

COPY --from=builder /app/build/libs/app.jar .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]