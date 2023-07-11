#!/usr/bin/env bash
set -e

docker run --rm -p 8080:8080 --name data-redis data/data-redis:checkpoint
