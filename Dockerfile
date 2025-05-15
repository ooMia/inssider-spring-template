# syntax=docker/dockerfile:latest

# === [1] Base Stage: 공통 베이스 이미지 및 유틸리티 설치 ===
FROM bellsoft/liberica-openjdk-alpine:24 AS base
LABEL maintainer="ooMia"
RUN apk add --no-cache dos2unix

# === [2] Builder Stage: Gradle 빌드 및 의존성 캐시 활용 ===
FROM base AS builder
WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY .java-version .java-version

ENV GRADLE_USER_HOME=/gradle-cache
RUN dos2unix gradlew && ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew build -x test --parallel --no-daemon

# === [3] Runner Stage: 실행용 경량 이미지 생성 ===
FROM bellsoft/liberica-openjre-alpine:24 AS runner
WORKDIR /app
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD ["sh", "-c", "wget -qO- http://localhost:8080/actuator/health || exit 1"]
ENTRYPOINT ["java", "-jar", "app.jar"]
