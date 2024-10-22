#!/bin/zsh

echo "INFO Удаление контейнера tflowers-postgres, если существует"
docker stop tflowers-postgres || true && docker rm tflowers-postgres || true
echo "INFO Запуск контейнера tflowers-postgres"
docker run --name tflowers-postgres -p 5432:5432 -e POSTGRES_USER=tflowers -e POSTGRES_PASSWORD=tflowers -e POSGRES_DB=tflowers -d postgres:15
docker ps -a -f name=tflowers-postgres

sleep 5

echo "INFO Накатка миграций"
liquibase --defaults-file=./liquibase.properties update

echo "INFO Удаление контейнера tflowers-redis, если существует"
docker stop tflowers-redis || true && docker rm tflowers-redis || true
echo "INFO Запуск контейнера tflowers-redis"
docker run --name tflowers-redis -p 6379:6379 -d redis:7.4.0 redis-server
docker ps -a -f name=tflowers-redis
