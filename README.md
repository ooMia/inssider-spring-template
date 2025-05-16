# initialization

```sh
# https://sdkman.io/install/
sdk install java 24-tem

# https://github.com/pinterest/ktlint
brew install ktlint
```

# start development

```sh
docker compose down && docker compose --profile dev up -d
compose build --check
ktlint --format

# 현재 터미널 세션에 환경변수 적용 후 테스트
export $(grep -v '^#' .env | xargs) && gradlew test -t

# 빌드 1: 현재 터미널 세션에 환경변수 적용 후 빌드
export $(grep -v '^#' .env | xargs) && gradlew clean build --info

# 빌드 2: profile=dev 상태로 빌드 
gradlew clean build -Dspring.profiles.active=dev

# 실행 1: gradlew profile=dev 상태로 실행
gradlew bootRun -Dspring.profiles.active=dev

# 실행 2: IDE extension으로 실행
```

# operation

```sh
docker compose down && COMPOSE_BAKE=true docker compose --profile prod up -d --build
```
