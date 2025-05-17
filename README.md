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
docker compose --profile dev up -d

# profile=dev 자동 초기화 및 멱등성 보장
gradlew test -Dspring.profiles.active=dev -t
gradlew bootRun -Dspring.profiles.active=dev
# + IDE extension으로도 실행 가능
```

# stage

```sh
docker compose --profile stage down --remove-orphans --volumes

COMPOSE_BAKE=true docker compose --profile stage up --build
```
