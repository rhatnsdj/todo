# 1단계: 빌드 단계
FROM gradle:7.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# 2단계: 실행 단계
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Render가 제공하는 PORT 환경변수 사용
ENV PORT=8080

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
