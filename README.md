# operation

```sh
docker compose pull
export $(grep -v '^#' .env | xargs)
COMPOSE_BAKE=true docker compose up -d
```
