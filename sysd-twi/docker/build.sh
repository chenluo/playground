#/bin/bash

docker build -t nginx-lb ./nginx
docker build -t read-app ../read
docker build -t fanout-app ../fanout
docker build -t user-app ../user
docker build -t write-app ../write
