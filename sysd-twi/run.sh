#!/bin/bash

WORKSPACE=$(pwd)

./gradlew build

cd $WORKSPACE/docker
bash build.sh

cd $WORKSPACE/docker
docker-compose up -d --build

cd $WORKSPACE

#nohup ./gradlew client:run > client.log 2>&1 &
./gradlew client:run --no-daemon
