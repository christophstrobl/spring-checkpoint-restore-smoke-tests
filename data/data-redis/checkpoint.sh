#!/usr/bin/env bash
set -e

url="https://cdn.azul.com/zulu/bin/zulu17.42.21-ca-crac-jdk17.0.7-linux_x64.tar.gz"

echo "Using CRaC enabled JACK $url"

../.././gradlew :data:data-redis:appTest
docker build -t data/data-redis:builder --build-arg CRAC_JDK_URL=$url .
docker run -d --privileged --rm --name=data-redis-demo --ulimit nofile=1024 -p 8080:8080 -v $PWD/build:/opt/mnt data/data-redis:builder
echo "Please wait during creating the checkpoint..."
sleep 50
docker commit --change='ENTRYPOINT ["/opt/app/entrypoint.sh"]' $(docker ps -qf "name=data-redis-demo") data/data-redis:checkpoint
docker kill $(docker ps -qf "name=data-redis-demo")
