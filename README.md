# initialization

```sh
git clone https://github.com/ooMia/inssider-spring-template.git
cd inssider-spring-template
cp .env.example .env

# https://sdkman.io/install/
sdk install java 24-tem
java --version

# https://github.com/pinterest/ktlint
brew install ktlint
ktlint --format

docker build --check .
```

# development

```sh
docker compose down --volumes --remove-orphans
docker compose up -d

# profile=dev 자동 초기화 및 멱등성 보장
gradlew test -Dspring.profiles.active=dev -t
gradlew bootRun -Dspring.profiles.active=dev
# + IDE extension으로도 실행 가능

docker exec -it inssider-spring-template-postgres-1 psql -U user -d dev
SELECT * FROM users;
```

# stage

```sh
docker compose --profile stage up --wait
```

# production

```sh
# cold-start 상황에서 데이터베이스 초기화
# SPRING_PROFILES_ACTIVE=stage docker compose --profile prod up --wait

# 이미지 최신화
docker compose --profile prod pull

docker compose --profile prod up
```