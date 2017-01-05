#!/bin/sh
sudo call /opt/zookeeper/bin/zkServer.sh &
sudo redis-server /opt/redis/redis.conf &
