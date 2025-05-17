# initialization

```sh
# https://sdkman.io/install/
sdk install java 24-tem
java --version

# https://github.com/pinterest/ktlint
brew install ktlint
ktlint --format

docker compose down && docker compose up -d
docker build --check .
```

# start development

```sh
# 현재 터미널 세션에 환경변수 적용 후 작업
export $(grep -v '^#' .env.example | xargs) 

# profile=dev 멱등성 보장
gradlew test -Dspring.profiles.active=dev -t
gradlew clean build -Dspring.profiles.active=dev --info
gradlew bootRun -Dspring.profiles.active=dev
# + IDE extension으로도 실행 가능
```

# operation

```sh
docker compose down && COMPOSE_BAKE=true docker compose --profile prod up -d --build
```